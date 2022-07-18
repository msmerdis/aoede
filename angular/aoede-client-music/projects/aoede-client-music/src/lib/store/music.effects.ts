import { Injectable, Inject } from '@angular/core';
import { Store } from '@ngrx/store';
import { Actions, createEffect, ofType, concatLatestFrom, OnInitEffects } from '@ngrx/effects';
import { Observable, of } from 'rxjs';
import { tap, map, filter, switchMap, catchError, delay } from 'rxjs/operators';
import { Router } from '@angular/router';

import { Sheet } from '../model/sheet.model';
import {
	musicEffectsInit,
	fetchSheetRequest,
	fetchSheetSuccess,
	fetchSheetFailure,
	fetchSheetListRequest,
	fetchSheetListSuccess,
	fetchSheetListFailure,
	preloadRequest,
	preloadSuccess,
	preloadFailure
} from './music.actions';
import {
	getGenericPayload,
	getRequestSuccess,
	getRequestFailure
} from 'aoede-client-generic';
import { MusicState } from './music.reducer';
import { MusicService } from '../music.service';
import { getSheetValue, getSheetListUTime } from './music.selectors';
import { MusicConfig, MusicConfigToken } from '../music.config';

@Injectable()
export class MusicEffects implements OnInitEffects {

	constructor (
		private store    : Store<MusicState>,
		private actions$ : Actions,
		private service  : MusicService,
		private router   : Router,
		@Inject(MusicConfigToken) private config : MusicConfig
	) { }

	ngrxOnInitEffects() {
		return musicEffectsInit();
	}

	fetchSheet$ = createEffect(
		() => this.actions$.pipe(
			ofType(fetchSheetRequest),
			concatLatestFrom(() => this.store.select(getSheetValue)),
			filter(([details, sheet]) => sheet === null || details.payload !== sheet.id),
			switchMap(([details, sheet]) => this.service.getSheet(details.payload).pipe(
					map(data => {
						return fetchSheetSuccess(
							getRequestSuccess(details, data)
						);
					}),
					catchError(err => of(fetchSheetFailure(
						getRequestFailure(details, err.error)
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
						getRequestFailure(details, err.error)
					)))
				)
			)
		)
	);

	triggerPreload$ = createEffect(
		() => this.actions$.pipe(
			ofType(musicEffectsInit, preloadFailure),
			switchMap((details) => of(preloadRequest (getGenericPayload())))
		)
	);

	preload$ = createEffect(
		() => this.actions$.pipe(
			ofType(preloadRequest),
			switchMap((details) => this.service.getPreload().pipe(
					map(data => {
						return preloadSuccess(
							getRequestSuccess(details, data)
						);
					}),
					catchError(err => of(preloadFailure(
						getRequestFailure(details, err.error)
					)).pipe(delay(this.config.retryTime!!)))
				)
			)
		)
	);

}
