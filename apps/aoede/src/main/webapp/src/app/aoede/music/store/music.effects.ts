import { Injectable, Inject } from '@angular/core';
import { Store } from '@ngrx/store';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { Observable, of } from 'rxjs';
import { tap, map, switchMap, catchError } from 'rxjs/operators';
import { Router } from '@angular/router';

import { Sheet } from '../model/sheet.model';
import {
	fetchSheetRequest,
	fetchSheetSuccess,
	fetchSheetFailure,
	fetchSheetListRequest,
	fetchSheetListSuccess,
	fetchSheetListFailure
} from './music.actions';
import { MusicState } from './music.reducer';
import { MusicService } from '../music.service';
import { getMusicState } from './music.selectors';
import { MusicConfig, MusicConfigToken } from '../music.config';

@Injectable()
export class MusicEffects {

	constructor (
		private store    : Store<MusicState>,
		private actions$ : Actions,
		private service  : MusicService,
		private router   : Router,
		@Inject(MusicConfigToken) private config : MusicConfig
	) { }

	fetchSheet$ = createEffect(
		() => this.actions$.pipe(
			ofType(fetchSheetRequest),
			switchMap((details) => this.service.getSheet(details.payload).pipe(
					map(resp => {
						return fetchSheetSuccess({
							success: {
								sheet : resp.body!!
							}
						});
					}),
					catchError(err => of(fetchSheetFailure({failure: err})))
				)
			)
		)
	);

	fetchSheetList$ = createEffect(
		() => this.actions$.pipe(
			ofType(fetchSheetListRequest),
			switchMap(() => this.service.getSheetList().pipe(
					map(resp => {
						return fetchSheetListSuccess({
							success: {
								sheetList : resp.body!!
							}
						});
					}),
					catchError(err => of(fetchSheetListFailure({failure: err})))
				)
			)
		)
	);

}
