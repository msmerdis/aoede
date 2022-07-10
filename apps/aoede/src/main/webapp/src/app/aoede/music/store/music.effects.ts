import { Injectable, Inject } from '@angular/core';
import { Store } from '@ngrx/store';
import { Actions, createEffect, ofType, concatLatestFrom } from '@ngrx/effects';
import { Observable, of } from 'rxjs';
import { tap, map, filter, switchMap, catchError } from 'rxjs/operators';
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
import {
	ApiGeneric,
	ApiRequest,
	ApiSuccess,
	ApiFailure,
	ApiError
} from '../../generic/generic-api.model';
import {
	getRequestSuccess,
	getRequestFailure
} from '../../generic/generic-store.model';
import { MusicState } from './music.reducer';
import { MusicService } from '../music.service';
import { getSheet, getSheetListUTime } from './music.selectors';
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
			concatLatestFrom(() => [
				this.store.select(getSheet),
				of(Date.now())
			]),
			filter(([details, sheet, current]) => sheet.utime + this.config.cacheTime!! <= current || sheet.value == null || details.payload !== sheet.value.id),
			switchMap(([details, sheet, current]) => this.service.getSheet(details.payload).pipe(
					map(data => {
						return fetchSheetSuccess(
							getRequestSuccess(details, data)
						);
					}),
					catchError(err => of(fetchSheetFailure(
						getRequestFailure(details, err)
					)))
				)
			)
		)
	);

	fetchSheetList$ = createEffect(
		() => this.actions$.pipe(
			ofType(fetchSheetListRequest),
			concatLatestFrom(() => [
				this.store.select(getSheetListUTime),
				of(Date.now())
			]),
			filter(([details, utime, current]) => utime + this.config.cacheTime!! <= current),
			switchMap(([details, utime, current]) => this.service.getSheetList().pipe(
					map(data => {
						return fetchSheetListSuccess(
							getRequestSuccess(details, data)
						);
					}),
					catchError(err => of(fetchSheetFailure(
						getRequestFailure(details, err)
					)))
				)
			)
		)
	);

}
