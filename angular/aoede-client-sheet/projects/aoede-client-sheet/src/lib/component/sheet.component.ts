import { Component, ViewChild, ElementRef, ChangeDetectorRef, Input, Output, EventEmitter, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription, Observable, of, combineLatest, throwError } from 'rxjs';
import { tap, map, take, filter, switchMap } from 'rxjs/operators';

import { Sheet, sheetInitializer } from '../model/sheet.model';
import { SheetConfiguration, sheetConfigurationInitializer } from '../model/sheet-configuration.model';
import { Track } from '../model/track.model';
import { Measure } from '../model/measure.model';
import { Clef, ClefArray } from '../model/clef.model';
import { KeySignature, KeySignatureArray } from '../model/key-signature.model';
import { Bar, Stave } from '../model/stave.model';
import { TrackInfo, trackInfoInitializer } from '../model/track-info.model';

@Component({
	selector: 'aoede-sheet',
	templateUrl: './sheet.component.html',
	styleUrls: ['./sheet.component.scss']
})
export class SheetComponent implements OnInit, OnDestroy {

	@Input() sheet  : Sheet              = sheetInitializer;
	@Input() config : SheetConfiguration = sheetConfigurationInitializer;

	@Output() modifiedEvent = new EventEmitter<boolean>();
	@Output() trackEvent    = new EventEmitter<number> ();

	public modified : boolean = false;

	public tracks : TrackInfo[] = [];
	public track  : number      = -1;
	public staves : number      =  0;

	private context  : CanvasRenderingContext2D | null = null;

	private stavesMargin  : number =  11 * 5;
	private stavesPadding : number =  11 * 5;
	private stavesWidth   : number = 188 * 5;
	private stavesHeight  : number =  33 * 5;
	private headerHeight  : number =  22 * 5;
	private footerHeight  : number =  11 * 5;
	private noteSpacing   : number =       5;

	@ViewChild('sCanvas') set content (content: ElementRef) {
		if (content != null) {
			this.context = content.nativeElement.getContext('2d');
		}
	}

	constructor(
		private route : ActivatedRoute,
		private cdref : ChangeDetectorRef
	) {}

	ngOnInit () : void {
		if (this.sheet === null) {
			this.tracks = [];
			this.track  = -1;
			this.staves =  0;
			return;
		}

		this.sheet.tracks.forEach((track, index) =>
			this.tracks.push(
				this.extractTrackInfo(this.sheet, track, index)
			)
		);

		if (this.tracks.length == 0) {
			this.track  = -1;
			this.staves =  0;
			return;
		}

		// display the first track
		this.updateTrack(this.config.firstTrack < this.tracks.length ? this.config.firstTrack : 0);
	}

	ngOnDestroy (): void {
	}

	public sheetHeight (staves : number, showHeader : boolean = false, showFooter : boolean = false) : number {
		return this.stavesHeight * staves +
			this.headerHeight * +showHeader +
			this.footerHeight * +showFooter;
	}

	public sheetWidth () : number {
		return this.stavesWidth + 2 * this.stavesMargin;
	}

	private drawCanvas () {
		this.cdref.detectChanges();
		if (this.context !== null && this.track >= 0) {
			this.context.save();
			this.context.clearRect(0, 0, this.sheetWidth(), this.sheetHeight(this.staves, this.config.showHeader, this.config.showFooter));

			// extract the correct track to draw
			var track = this.tracks[this.track];

			if (this.config.showHeader)
				this.drawTitle(this.context, 0, this.headerHeight, track.title + " - " + track.name);

			for (var i = 0; i < track.staves.length; i += 1) {
				this.drawStave(this.context, i, track, track.staves[i]);
			}

			if (this.config.showFooter)
				this.drawTitle(this.context, this.sheetHeight(this.staves, this.config.showHeader, false), this.footerHeight, "- " + this.track + " -");
			this.context.restore();
		}
	}

	public extractTrackInfo (sheet : Sheet, track : Track, trackIdx : number) : TrackInfo {
		return {
			title         : sheet.name,
			name          : track.name != null ? track.name : "Track " + trackIdx,
			clef          : this.config.clefArray[track.clef],
			tempo         : track.tempo,
			keySignature  : this.config.keysArray[track.keySignature],
			timeSignature : track.timeSignature,
			staves        : this.splitStaves(track)
		};
	}

	public splitStaves (track : Track) : Stave[] {
		return this.splitBars (track).reduce ((staves : Stave[], bar : Bar) : Stave[] => {
			var bottom = staves[staves.length - 1];

			if (bottom.width + bar.width > this.stavesWidth) {
				bottom = this.emptyStave();
				staves.push(bottom);
			}

			bottom.width += bar.width;
			bottom.bars.push (bar);

			return staves;
		}, [this.emptyStave()] as Stave[]);
	}

	private splitBars (track : Track) : Bar[] {
		return track.measures.reduce ((bars : Bar[], measure : Measure) : Bar[] => {
			bars.push ({
				measure : measure,
				width   : this.barWidth(measure),
				offset  : 0
			});

			return bars;
		}, [] as Bar[]);
	}

	private barWidth (measure : Measure) : number {
		return 200;
	}

	private emptyStave () : Stave {
		return {
			bars   : [],
			width  : this.stavesPadding,
			offset : 0
		};
	}

	public updateTrack (track : number) {
		if (track < this.tracks.length) {
			this.track = track;
			this.trackEvent.emit(track);
			this.staves = this.tracks[this.track].staves.length;
			this.drawCanvas ();
		}
	}

	public setupStave (context: CanvasRenderingContext2D, s : number, e : number, m : number, w : number) {
		context.fillStyle = 'black';
		context.fillRect(s, m - this.noteSpacing * 4, w, 1);
		context.fillRect(s, m - this.noteSpacing * 2, w, 1);
		context.fillRect(s, m                       , w, 1);
		context.fillRect(s, m + this.noteSpacing * 2, w, 1);
		context.fillRect(s, m + this.noteSpacing * 4, w, 1);
		context.fillRect(e, m - this.noteSpacing * 4, 1, this.noteSpacing * 8 + 1);
	}

	public drawTitle (context: CanvasRenderingContext2D, y : number, h : number, text : string) {
		context.font = (h/2) + 'px consolas';
		context.textAlign = "center";
		context.fillStyle = 'black';
		context.fillText(text, this.stavesMargin + (this.stavesWidth + 1) / 2, y + 3 * h / 4 - 1);
	}

	public drawStave (context: CanvasRenderingContext2D, i : number, info : TrackInfo, stave : Stave) {
		var m : number = this.sheetHeight(i, this.config.showHeader, false) + (this.stavesHeight + 1) / 2;

		this.setupStave (context, this.stavesMargin, this.stavesWidth + this.stavesMargin, m, this.stavesWidth);
		this.drawClef  (context, this.stavesMargin + this.noteSpacing * 4 + 1, m, info.clef);
	}

	public drawClef (context: CanvasRenderingContext2D, x : number, y : number, clef : Clef) {
		context.fillStyle = 'yellow';
		context.fillRect(x - this.noteSpacing * 2, y - this.noteSpacing * (2 + clef.spos), this.noteSpacing * 4, this.noteSpacing * 4);
	}

}
