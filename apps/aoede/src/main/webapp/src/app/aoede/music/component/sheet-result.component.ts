import { Component, OnInit, OnDestroy } from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { take, tap, filter } from 'rxjs/operators';
import { Store } from '@ngrx/store';

import { Sheet } from '../model/sheet.model';
import { KeySignature } from '../model/key-signature.model';
import { MusicState } from '../store/music.reducer';
import { fetchSheetListRequest } from '../store/music.actions';
import { getSheetListValue, getKeysValue } from '../store/music.selectors';
import { getGenericPayload } from '../../generic/generic-store.model';


@Component({
	selector: 'aoede-music-sheet-result',
	templateUrl: './sheet-result.component.html',
	styleUrls: ['./sheet-result.component.scss']
})
export class SheetResultComponent implements OnInit {

	loaded     : boolean = false;
	sheetList$ : Observable<Sheet[] | null>;
	keysList   : KeySignature[] = [];
	keysSub    : Subscription   = Subscription.EMPTY;

	constructor(
		private store : Store<MusicState>
	) {
		this.sheetList$ = this.store.select (getSheetListValue);
	}

	ngOnInit() {
		this.store.dispatch (
			fetchSheetListRequest(getGenericPayload())
		);

		this.store.select(getKeysValue).pipe(
			filter(keys => keys ? keys.length > 0 : false),
			take(1),
		).subscribe(keys => this.keysList = (keys || []));
	}

	ngOnDestroy() {
		this.keysSub.unsubscribe();
	}

	translateKey (key : number) : string {
		var k;

		if (k = this.keysList.find(k => k.id == key)) {
			return k.major + " / " + k.minor;
		}

		return "-";
	}

}
