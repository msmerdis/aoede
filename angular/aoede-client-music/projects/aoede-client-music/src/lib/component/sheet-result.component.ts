import { Component, OnInit, OnDestroy } from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { take, tap, filter } from 'rxjs/operators';
import { Store } from '@ngrx/store';

import {
	Sheet,
	KeySignature,
	TimeSignature
} from 'aoede-client-sheet';
import { MusicState } from '../store/music.reducer';
import { fetchSheetListRequest } from '../store/music.actions';
import { getSheetListValue, getKeys } from '../store/music.selectors';
import { getGenericPayload } from 'aoede-client-generic';


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
	unknown    : string = "-";

	constructor(
		private store : Store<MusicState>
	) {
		this.sheetList$ = this.store.select (getSheetListValue);
	}

	ngOnInit() {
		this.store.dispatch (
			fetchSheetListRequest(getGenericPayload())
		);

		this.store.select(getKeys).pipe(
			filter(keys => keys ? keys.length > 0 : false),
			take(1),
		).subscribe(keys => this.keysList = (keys || []));
	}

	ngOnDestroy() {
		this.keysSub.unsubscribe();
	}

	translateKey (key : number | undefined) : string {
		var k;

		if (key !== undefined) {
			if (k = this.keysList.find(k => k.id == key)) {
				return k.major + " / " + k.minor;
			}
		}

		return this.unknown;
	}

	translateTime (time : TimeSignature | undefined) : string {
		if (time !== undefined) {
			return time.numerator + " / " + time.denominator;
		}

		return this.unknown;
	}

}
