import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';

import { Sheet } from '../model/sheet.model';
import { MusicState } from '../store/music.reducer';
import { getSheet } from '../store/music.selectors';

@Component({
	selector: 'aoede-music-sheet',
	templateUrl: './sheet.component.html',
	styleUrls: ['./sheet.component.scss']
})
export class SheetComponent {

	sheet$ : Observable<Sheet | null>;

	constructor(
		private store : Store<MusicState>
	) {
		this.sheet$ = this.store.select (getSheet);
	}

}
