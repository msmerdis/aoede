import { Component, OnDestroy, ViewChild, ElementRef, ChangeDetectorRef } from '@angular/core';
import { Subscription } from 'rxjs';
import { Store } from '@ngrx/store';
import { ActivatedRoute } from '@angular/router';
import { tap, map, filter, switchMap } from 'rxjs/operators';

import { Sheet } from '../model/sheet.model';
import { Bar, Line } from '../model/line.model';
import { TrackInfo, trackInfoInitializer } from '../model/track-info.model';
import { MusicState } from '../store/music.reducer';
import { fetchSheetRequest } from '../store/music.actions';
import { getSheetValueSafe } from '../store/music.selectors';
import { getRequestPayload } from '../../generic/generic-store.model';
import { MusicFacadeService } from '../music-facade.service';
import { MusicCanvasService } from '../music-canvas.service';

@Component({
	selector: 'aoede-music-sheet',
	templateUrl: './sheet.component.html',
	styleUrls: ['./sheet.component.scss']
})
export class SheetComponent implements OnDestroy {

	private id : number = 0;
	private sheet$ : Subscription;

	public track  : number = -1;
	public lines  : number = 0;
	public width  : number;
	public height : number;
	public header : number;
	public footer : number;

	private context : CanvasRenderingContext2D | null = null;

	@ViewChild('sCanvas') set content (content: ElementRef) {
		if (content != null) {
			this.context = content.nativeElement.getContext('2d');
			this.drawCanvas ();
		}
	}

	private modified : boolean     = false;
	public  tracks   : TrackInfo[] = [];

	constructor(
		private store  : Store<MusicState>,
		private route  : ActivatedRoute,
		private facade : MusicFacadeService,
		private paint  : MusicCanvasService,
		private cdref  : ChangeDetectorRef
	) {
		this.sheet$ = this.route.params.pipe (
			map(params => +params['id']),
			tap(id => this.dispatch(id)),
			switchMap(id => this.store.select (getSheetValueSafe, id))
		).subscribe ((sheet) => {
			if (this.modified)
				return;

			if (sheet === null) {
				this.tracks = [];
				this.lines  =  0;
				this.track  = -1;
				return;
			}

			sheet.tracks.forEach((currentValue, index) =>
				this.facade.extractTrackInfo(sheet, index).subscribe(
					(track) => this.tracks.push(track)
				).unsubscribe()
			);

			// by default display the first track
			if (this.tracks.length > 0) {
				this.lines = this.tracks[0].lines.length;
				this.track = 0;
			}
		});

		this.width  = this.paint.lineWidth;
		this.height = this.paint.lineHeight;
		this.header = this.paint.headerHeight;
		this.footer = this.paint.footerHeight;
	}

	ngOnDestroy (): void {
		this.sheet$.unsubscribe ();
	}

	private dispatch(id : number) {
		if (this.id == id)
			return;

		this.id = id;
		this.store.dispatch (fetchSheetRequest(
			getRequestPayload<number>(id)
		));
	}

	private drawCanvas () {
		this.cdref.detectChanges();
		if (this.context !== null && this.track >= 0) {
			this.context.save();

			// extract the correct track to draw
			var track = this.tracks[this.track];

			this.paint.clearArea(this.context, 0, 0, this.width, this.header + this.height * this.lines + this.footer);

			this.paint.drawTitle(this.context, 0, 0, this.width, this.header, track.title + " - " + track.name);
			for (var i = 0; i < track.lines.length; i += 1) {
				this.paint.drawLine(this.context, 0, this.header + this.height * i, this.width, this.height, track, track.lines[i]);
			}
			this.paint.drawTitle(this.context, 0, this.header + this.height * i, this.width, this.footer, "- " + this.id + " -");
			this.context.restore();
		}
	}

	public prev () {
		if (this.lines > 1)
			this.lines -= 1;
		this.drawCanvas ();
	}

	public next () {
		this.lines += 1;
		this.drawCanvas ();
	}
}
