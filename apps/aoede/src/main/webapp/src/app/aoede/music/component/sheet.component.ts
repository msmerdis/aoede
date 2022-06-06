import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';
import { ActivatedRoute } from '@angular/router';

import { Sheet } from '../model/sheet.model';
import { MusicState } from '../store/music.reducer';
import { fetchSheetRequest } from '../store/music.actions';
import { getSheet } from '../store/music.selectors';

@Component({
	selector: 'aoede-music-sheet',
	templateUrl: './sheet.component.html',
	styleUrls: ['./sheet.component.scss']
})
export class SheetComponent {

	sheet$ : Observable<Sheet | null>;

	constructor(
		private store : Store<MusicState>,
		private route : ActivatedRoute
	) {
		this.sheet$ = this.store.select (getSheet);
	}

	ngOnInit() {
		this.store.dispatch (fetchSheetRequest({
			payload : +this.route.snapshot.paramMap.get('id')!!
		}));
	}
}
