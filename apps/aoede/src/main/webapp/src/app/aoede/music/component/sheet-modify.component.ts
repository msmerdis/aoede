import { Component } from '@angular/core';
import { Store } from '@ngrx/store';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { tap, map, filter, switchMap } from 'rxjs/operators';

import { Sheet } from '../model/sheet.model';
import { Bar, Line } from '../model/line.model';
import { TrackInfo, trackInfoInitializer } from '../model/track-info.model';
import { MusicState } from '../store/music.reducer';
import { fetchSheetRequest } from '../store/music.actions';
import { getSheetValueSafe } from '../store/music.selectors';
import { getRequestPayload } from '../../generic/generic-store.model';

@Component({
	selector: 'aoede-music-sheet-modify',
	templateUrl: './sheet-modify.component.html',
	styleUrls: ['./sheet-modify.component.scss']
})
export class SheetModifyComponent {

	private id    : number = 0;
	public sheet$ : Observable<Sheet | null>;

	constructor(
		private store  : Store<MusicState>,
		private route  : ActivatedRoute
	) {
		this.sheet$ = this.route.params.pipe (
			map(params => +params['sheet']),
			tap(id => this.dispatch(id)),
			switchMap(id => this.store.select (getSheetValueSafe, id))
		)
	}

	private dispatch(id : number) {
		if (this.id == id)
			return;

		this.id = id;
		this.store.dispatch (fetchSheetRequest(
			getRequestPayload<number>(id)
		));
	}

}
