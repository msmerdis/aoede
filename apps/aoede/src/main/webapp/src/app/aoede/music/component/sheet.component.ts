import { Component, ViewChild, ElementRef, ChangeDetectorRef, Input, OnInit, OnChanges, SimpleChanges, OnDestroy } from '@angular/core';
import { Store } from '@ngrx/store';
import { Subscription, Observable, of, combineLatest, throwError } from 'rxjs';
import { tap, map, take, filter, switchMap } from 'rxjs/operators';

import { Sheet } from '../model/sheet.model';
import { Track } from '../model/track.model';
import { Measure } from '../model/measure.model';
import { Clef } from '../model/clef.model';
import { KeySignature } from '../model/key-signature.model';
import { Bar, Line } from '../model/line.model';
import { TrackInfo, trackInfoInitializer } from '../model/track-info.model';

import { MusicState } from '../store/music.reducer';
import { fetchSheetRequest } from '../store/music.actions';
import {
	getClef,
	getKeySignature,
	getSheetValueSafe
} from '../store/music.selectors';
import { getRequestPayload } from '../../generic/generic-store.model';

@Component({
	selector: 'aoede-music-sheet',
	templateUrl: './sheet.component.html',
	styleUrls: ['./sheet.component.scss']
})
export class SheetComponent implements OnInit, OnDestroy {

	@Input() sheet$ : Observable<Sheet | null>;
	public modified : boolean = false;

	public tracks : TrackInfo[] = [];
	public track  : number      = -1;
	public lines  : number = 0;
	public width  : number;
	public height : number;
	public header : number;
	public footer : number;

	private context  : CanvasRenderingContext2D | null = null;
	private sheetSub : Subscription = Subscription.EMPTY;

	@ViewChild('sCanvas') set content (content: ElementRef) {
		if (content != null) {
			this.context = content.nativeElement.getContext('2d');
			this.drawCanvas ();
		}
	}

	constructor(
		private store  : Store<MusicState>,
		private cdref  : ChangeDetectorRef
	) {
		this.sheet$ = of(null);
		this.width  = this.lineWidth;
		this.height = this.lineHeight;
		this.header = this.headerHeight;
		this.footer = this.footerHeight;
	}

	ngOnInit () : void {
		this.sheetSub = this.sheet$
			.pipe(filter(() => this.modified == false))
			.subscribe ((sheet) => {
				if (sheet === null) {
					this.tracks = [];
					this.track  = -1;
					this.lines  =  0;
					return;
				}

				sheet.tracks.forEach((currentValue, index) =>
					this.extractTrackInfo(sheet, index).subscribe(
						(track) => this.tracks.push(track)
					).unsubscribe()
				);

				if (this.tracks.length == 0) {
					this.track = -1;
					this.lines =  0;
					return;
				}

				// by default display the first track
				this.updateTrack(0);
			});
	}

	ngOnDestroy (): void {
		this.sheetSub.unsubscribe ();
	}

	private drawCanvas () {
		this.cdref.detectChanges();
		if (this.context !== null && this.track >= 0) {
			this.context.save();

			// extract the correct track to draw
			var track = this.tracks[this.track];

			this.clearArea(this.context, 0, 0, this.width, this.header + this.height * this.lines + this.footer);

			this.drawTitle(this.context, 0, 0, this.width, this.header, track.title + " - " + track.name);
			for (var i = 0; i < track.lines.length; i += 1) {
				this.drawLine(this.context, 0, this.header + this.height * i, this.width, this.height, track, track.lines[i]);
			}
			this.drawTitle(this.context, 0, this.header + this.height * i, this.width, this.footer, "- " + this.track + " -");
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
			this.store.select(getKeySignature, track.keySignature),
			(clef : Clef | null, key : KeySignature | null) => {
				return {
					title         : sheet.name,
					name          : track.name != null ? track.name : "Track " + trackIdx,
					clef          : clef!!,
					tempo         : track.tempo,
					keySignature  : key!!,
					timeSignature : track.timeSignature,
					lines         : this.splitLines(track)
				};
			}
		).pipe(take(1));
	}

	public updateTrack (track : number) {
		if (track < this.tracks.length) {
			this.track = track;
			this.lines = this.tracks[this.track].lines.length;
			this.drawCanvas ();
		}
	}

	public lineMargin   : number =  11 * 5;
	public linePadding  : number =  11 * 5;
	public lineWidth    : number = 210 * 5;
	public lineHeight   : number =  33 * 5;
	public headerHeight : number =  22 * 5;
	public footerHeight : number =  11 * 5;
	public noteSpacing  : number =       5;

	public lineOverhead : number = this.lineMargin * 2 + this.linePadding;

	public splitLines (track : Track) : Line[] {
		return this.splitBars (track).reduce ((lines : Line[], bar : Bar) : Line[] => {
			var bottom = lines[lines.length - 1];

			if (bottom.width + bar.width > this.lineWidth) {
				bottom = this.emptyLine();
				lines.push(bottom);
			}

			bottom.width += bar.width;
			bottom.bars.push (bar);

			return lines;
		}, [this.emptyLine()] as Line[]);
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

	private emptyLine () : Line {
		return {
			bars  : [],
			width : this.lineOverhead
		};
	}

	public clearArea (context: CanvasRenderingContext2D, x : number, y : number, w : number, h : number) {
		context.clearRect(x, y, w, h);
	}

	public setupLine (context: CanvasRenderingContext2D, s : number, e : number, m : number, w : number) {
		context.fillStyle = 'black';
		context.fillRect(s, m - this.noteSpacing * 4, w, 1);
		context.fillRect(s, m - this.noteSpacing * 2, w, 1);
		context.fillRect(s, m                       , w, 1);
		context.fillRect(s, m + this.noteSpacing * 2, w, 1);
		context.fillRect(s, m + this.noteSpacing * 4, w, 1);
		context.fillRect(e, m - this.noteSpacing * 4, 1, this.noteSpacing * 8 + 1);
	}

	public drawTitle (context: CanvasRenderingContext2D, x : number, y : number, w : number, h : number, text : string) {
		context.font = (h/2) + 'px consolas';
		context.textAlign = "center";
		context.fillStyle = 'black';
		context.fillText(text, x + w/2, y + 3*h/4 - 1);
	}

	public drawLine (context: CanvasRenderingContext2D, x : number, y : number, w : number, h : number, info : TrackInfo, line : Line) {
		var m : number = y + h / 2;
		var e : number = w - this.lineMargin;
		var s : number = x + this.lineMargin;

		w -= this.lineMargin * 2;

		this.setupLine (context, s, e, m, w);
		this.drawClef  (context, s + this.noteSpacing * 4 + 1, m, info.clef);
	}

	public drawClef (context: CanvasRenderingContext2D, x : number, y : number, clef : Clef) {
		context.fillStyle = 'yellow';
		context.fillRect(x - this.noteSpacing * 2, y - this.noteSpacing * (2 + clef.spos), this.noteSpacing * 4, this.noteSpacing * 4);
	}

}
