import { Component, ViewChild, ElementRef, ChangeDetectorRef, Input, Output, EventEmitter, OnInit, OnDestroy } from '@angular/core';
import { Store } from '@ngrx/store';
import { ActivatedRoute } from '@angular/router';
import { Subscription, Observable, of, combineLatest, throwError } from 'rxjs';
import { tap, map, take, filter, switchMap } from 'rxjs/operators';

import { Sheet } from '../model/sheet.model';
import { Track } from '../model/track.model';
import { Measure } from '../model/measure.model';
import { Clef } from '../model/clef.model';
import { KeySignature } from '../model/key-signature.model';
import { Bar, Stave } from '../model/stave.model';
import { TrackInfo, trackInfoInitializer } from '../model/track-info.model';

import { MusicState } from '../store/music.reducer';
import { fetchSheetRequest } from '../store/music.actions';
import {
	getClef,
	getKey,
	getSheetValueSafe
} from '../store/music.selectors';
import { getRequestPayload } from '../../generic/generic-store.model';

@Component({
	selector: 'aoede-music-sheet',
	templateUrl: './sheet.component.html',
	styleUrls: ['./sheet.component.scss']
})
export class SheetComponent implements OnInit, OnDestroy {

	@Input() sheet$     : Observable<Sheet | null>;
	@Input() showHeader : boolean = false;
	@Input() showFooter : boolean = false;
	@Input() firstTrack : number  = 0;

	@Output() modifiedEvent = new EventEmitter<boolean>();
	@Output() trackEvent    = new EventEmitter<number> ();

	public modified : boolean = false;

	public tracks : TrackInfo[] = [];
	public track  : number      = -1;
	public staves : number      =  0;

	private context  : CanvasRenderingContext2D | null = null;
	private sheetSub : Subscription = Subscription.EMPTY;

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
		private store : Store<MusicState>,
		private route : ActivatedRoute,
		private cdref : ChangeDetectorRef
	) {
		this.sheet$ = of(null);
	}

	ngOnInit () : void {
		this.sheetSub = this.sheet$
			.pipe(filter(() => this.modified == false))
			.subscribe ((sheet) => {
				if (sheet === null) {
					this.tracks = [];
					this.track  = -1;
					this.staves =  0;
					return;
				}

				sheet.tracks.forEach((currentValue, index) =>
					this.extractTrackInfo(sheet, index).subscribe(
						(track) => this.tracks.push(track)
					).unsubscribe()
				);

				if (this.tracks.length == 0) {
					this.track  = -1;
					this.staves =  0;
					return;
				}

				// display the first track
				this.updateTrack(this.firstTrack < this.tracks.length ? this.firstTrack : 0);
			});
	}

	ngOnDestroy (): void {
		this.sheetSub.unsubscribe ();
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
			this.context.clearRect(0, 0, this.sheetWidth(), this.sheetHeight(this.staves, this.showHeader, this.showFooter));

			// extract the correct track to draw
			var track = this.tracks[this.track];

			if (this.showHeader)
				this.drawTitle(this.context, 0, this.headerHeight, track.title + " - " + track.name);

			for (var i = 0; i < track.staves.length; i += 1) {
				this.drawStave(this.context, i, track, track.staves[i]);
			}

			if (this.showFooter)
				this.drawTitle(this.context, this.sheetHeight(this.staves, this.showHeader, false), this.footerHeight, "- " + this.track + " -");
			this.context.restore();
		}
	}

	public extractTrackInfo (sheet : Sheet, trackIdx : number) : Observable<TrackInfo> {
		if (sheet.tracks.length <= trackIdx) {
			return throwError(() => new Error('track with index ' + trackIdx + ' not found'));
		}

		var track = sheet.tracks[trackIdx];

		return combineLatest(
			this.store.select(getClef, track.clef),
			this.store.select(getKey, track.keySignature),
			(clef : Clef | null, key : KeySignature | null) => {
				return {
					title         : sheet.name,
					name          : track.name != null ? track.name : "Track " + trackIdx,
					clef          : clef!!,
					tempo         : track.tempo,
					keySignature  : key!!,
					timeSignature : track.timeSignature,
					staves         : this.splitStaves(track)
				};
			}
		).pipe(take(1));
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
				width   : this.barWidth(measure)
			});

			return bars;
		}, [] as Bar[]);
	}

	private barWidth (measure : Measure) : number {
		return 200;
	}

	private emptyStave () : Stave {
		return {
			bars  : [],
			width : this.stavesPadding
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
		var m : number = this.sheetHeight(i, this.showHeader, false) + (this.stavesHeight + 1) / 2;

		this.setupStave (context, this.stavesMargin, this.stavesWidth + this.stavesMargin, m, this.stavesWidth);
		this.drawClef  (context, this.stavesMargin + this.noteSpacing * 4 + 1, m, info.clef);
	}

	public drawClef (context: CanvasRenderingContext2D, x : number, y : number, clef : Clef) {
		context.fillStyle = 'yellow';
		context.fillRect(x - this.noteSpacing * 2, y - this.noteSpacing * (2 + clef.spos), this.noteSpacing * 4, this.noteSpacing * 4);
	}

}
