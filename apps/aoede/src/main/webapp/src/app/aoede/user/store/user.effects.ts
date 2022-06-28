import { Injectable, Inject } from '@angular/core';
import { Store } from '@ngrx/store';
import { Actions, createEffect, ofType, concatLatestFrom, Effect } from '@ngrx/effects';
import { Observable, of, interval } from 'rxjs';
import { tap, map, mapTo, switchMap, skip, catchError } from 'rxjs/operators';
import { Router } from '@angular/router';

import { User } from '../model/user.model';
import { LoginDetails } from '../model/login-details.model';
import {
	loginRequest,
	loginSuccess,
	loginFailure,
	keepAliveRequest,
	keepAliveSuccess,
	keepAliveFailure
} from './user.actions';
import { UserState } from './user.reducer';
import { UserService } from '../user.service';
import { getUserState, getAuthToken } from './user.selectors';
import { UserConfig, UserConfigToken } from '../user.config';

@Injectable()
export class UserEffects {

	private cacheKey : string  = 'aoede-user-state';

	constructor (
		private store    : Store<UserState>,
		private actions$ : Actions,
		private service  : UserService,
		private router   : Router,
		@Inject(UserConfigToken) private config : UserConfig
	) {
		let state = localStorage.getItem(this.cacheKey);

		if (state === null)
			return;

		let userState = JSON.parse(state);

		if ((userState.time + this.config.tokenExpiry!!) < Date.now())
			return;

		this.store.dispatch (loginSuccess({
			success: userState
		}));
	}

	login$ = createEffect(
		() => this.actions$.pipe(
			ofType(loginRequest),
			concatLatestFrom(() => [
				of(Date.now())
			]),
			switchMap(([details, timestamp]) => this.service.login(details.payload).pipe(
				map(resp => {
					this.router.navigate(['']);
					return loginSuccess({
						success: {
							user: resp.body!!,
							token: resp.headers.get(this.config.authToken!!)!!,
							time: timestamp
						}
					});
				}),
				catchError(err => of(loginFailure({failure: err})))
			))
		)
	);

	keepAlive$ = createEffect(
		() => this.actions$.pipe(
			ofType(keepAliveRequest),
			concatLatestFrom(() => [
				this.store.select(getAuthToken),
				of(Date.now())
			]),
			switchMap(([action, token, timestamp]) => this.service.keepAlive(token).pipe(
				map(resp => {
					return keepAliveSuccess({
						success: {
							user: resp.body!!,
							token: resp.headers.get(this.config.authToken!!)!!,
							time: timestamp
						}
					});
				}),
				catchError(err => of(keepAliveFailure({failure: err})))
			))
		)
	);

	@Effect()
	keepAliveDispatcher$ = interval(this.config.tokenKeepAlive).pipe(
		mapTo(keepAliveRequest())
	);

	updateCache$ = createEffect(
		() => this.store.select (getUserState).pipe(
			// ignore first item from the state
			// otherwise it will fire up first with the initial state
			// clearing up the state
			skip(1),
			tap((state : UserState) => {
				if (state.authenticated) {
					localStorage.setItem(this.cacheKey, JSON.stringify({
						user  : state.user,
						token : state.authToken,
						time  : state.authTimestamp
					}));
				} else {
					localStorage.removeItem(this.cacheKey);
				}
			})
		),
		{ dispatch: false }
	);

}
