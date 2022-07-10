import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';
import { ActivatedRoute } from '@angular/router';
import { tap, map, switchMap } from 'rxjs/operators';

import { Sheet } from '../model/sheet.model';
import { MusicState } from '../store/music.reducer';
import { fetchSheetRequest } from '../store/music.actions';
import { getSheetValueSafe } from '../store/music.selectors';
import { getRequestPayload } from '../../generic/generic-store.model';

@Component({
	selector: 'aoede-music-sheet',
	templateUrl: './sheet.component.html',
	styleUrls: ['./sheet.component.scss']
})
export class SheetComponent {

	id : number = 0;
	sheet$ : Observable<Sheet | null>;

	constructor(
		private store : Store<MusicState>,
		private route : ActivatedRoute
	) {
		this.sheet$ = this.route.params.pipe(
			map(params => +params['id']),
			tap(id => this.dispatch(id)),
			switchMap(id => this.store.select (getSheetValueSafe, id))
		);
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
