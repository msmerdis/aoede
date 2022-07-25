import { Location } from '@angular/common';
import { Component } from '@angular/core';
import { Store } from '@ngrx/store';
import { Router, ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { take, tap, map, filter, switchMap } from 'rxjs/operators';

import {
	Sheet,
	sheetInitializer,
	Clef,
	KeySignature,
	SheetConfiguration,
	sheetConfigurationInitializer,
	StaveConfiguration,
	staveConfigurationInitializer
} from 'aoede-client-sheet';
import { MusicState } from '../store/music.reducer';
import { fetchSheetRequest } from '../store/music.actions';
import {
	getClefs,
	getKeys,
	getSheetValueSafe
} from '../store/music.selectors';
import { getRequestPayload } from 'aoede-client-generic';

@Component({
	selector: 'aoede-music-sheet-modify',
	templateUrl: './sheet-modify.component.html',
	styleUrls: ['./sheet-modify.component.scss']
})
export class SheetModifyComponent {

	private id    : number = 0;
	public sheet$ : Observable<Sheet | null>;
	public sheet  : Sheet  = sheetInitializer();
	public track  : number = 0;

	public sheetConfig1 : SheetConfiguration = {
		...sheetConfigurationInitializer(),
		showHeader : true,
		showFooter : true
	};

	public sheetConfig2 : SheetConfiguration = {
		...sheetConfigurationInitializer(),
		showHeader : true,
		showFooter : true
	};

	public staveConfig : StaveConfiguration = staveConfigurationInitializer();

	private modified : boolean = false;

	constructor(
		private store  : Store<MusicState>,
		private loc    : Location,
		private router : Router,
		private route  : ActivatedRoute
	) {
		this.sheet$ = this.route.params.pipe (
			take(1),
			map(params => +params['sheet']),
			tap(id => this.dispatch(id)),
			switchMap(id => this.store.select (getSheetValueSafe, id))
		).pipe (tap(
			(sheet) => this.sheet = sheet || sheetInitializer()
		));

		this.store.select(getClefs).pipe (
			filter((clefs) => clefs !== null),
			take (1),
			tap ((clefs) => clefs && clefs.forEach(
				(clef) => {
					this.sheetConfig1.clefArray[clef.id] = clef;
					this.sheetConfig2.clefArray[clef.id] = clef;
				})
			)
		).subscribe();

		this.store.select(getKeys).pipe (
			filter((keys) => keys !== null),
			take (1),
			tap ((keys) => keys && keys.forEach(
				(key) => {
					this.sheetConfig1.keysArray[key.id] = key;
					this.sheetConfig2.keysArray[key.id] = key;
				})
			)
		).subscribe();

		this.track = +this.route.snapshot.params['track']-1 || 0;
		this.sheetConfig1.showTracks = [this.track];
		this.sheetConfig2.showTracks = [0, this.track];
	}

	private dispatch(id : number) {
		if (this.id == id)
			return;

		this.id = id;
		this.store.dispatch (fetchSheetRequest(
			getRequestPayload<number>(id)
		));
	}

	public updateTrack (track : number) : void {
		const url = this
			.router
			.createUrlTree(['../', track + 1], {relativeTo: this.route})
			.toString();

		this.sheetConfig1 = {
			...this.sheetConfig1,
			showTracks : [track]
		}
		this.sheetConfig2 = {
			...this.sheetConfig2,
			showTracks : [0, track]
		}
		this.loc.go(url);
	}

	public updateScale (scale : number) : void {
		this.staveConfig = staveConfigurationInitializer(scale);
	}

	public updateNormalize (check : boolean) : void {

		this.sheetConfig1 = {
			...this.sheetConfig1,
			normalize : check
		}
		this.sheetConfig2 = {
			...this.sheetConfig2,
			normalize : check
		}
	}
}
