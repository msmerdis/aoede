import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';
import { ActivatedRoute } from '@angular/router';
import { tap, map, switchMap } from 'rxjs/operators';

import { Sheet } from '../model/sheet.model';
import { MusicState } from '../store/music.reducer';
import { fetchSheetRequest } from '../store/music.actions';
import { getSheetSafe } from '../store/music.selectors';

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
			switchMap(id => this.store.select (getSheetSafe, id))
		);
	}

	private dispatch(id : number) {
		if (this.id == id)
			return;

		this.id = id;
		this.store.dispatch (fetchSheetRequest({
			payload : id
		}));
	}
}
