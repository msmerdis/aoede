import { Location } from '@angular/common';
import { Component } from '@angular/core';
import { Store } from '@ngrx/store';
import { Router, ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { take, tap, map, filter, switchMap } from 'rxjs/operators';

import { Sheet, sheetInitializer } from 'aoede-client-sheet';
import { MusicState } from '../store/music.reducer';
import { fetchSheetRequest } from '../store/music.actions';
import { getSheetValueSafe } from '../store/music.selectors';
import { getRequestPayload } from 'aoede-client-generic';

@Component({
	selector: 'aoede-music-sheet-modify',
	templateUrl: './sheet-modify.component.html',
	styleUrls: ['./sheet-modify.component.scss']
})
export class SheetModifyComponent {

	private id    : number = 0;
	public sheet$ : Observable<Sheet | null>;
	public sheet  : Sheet  = sheetInitializer;
	public track  : number = 0;

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
			(sheet) => this.sheet = sheet || sheetInitializer
		));

		this.track = +this.route.snapshot.params['track']-1 || 0;
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

		this.loc.go(url);
	}

	public updateModified (modified : boolean) : void {
		this.modified = modified;
	}

}
