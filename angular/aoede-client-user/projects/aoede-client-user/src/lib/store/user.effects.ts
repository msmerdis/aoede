import { Injectable, Inject } from '@angular/core';
import { Store } from '@ngrx/store';
import { Actions, createEffect, ofType, concatLatestFrom, Effect } from '@ngrx/effects';
import { Observable, of, interval } from 'rxjs';
import { tap, map, mapTo, switchMap, skip, filter, catchError } from 'rxjs/operators';
import { Router } from '@angular/router';

import { User } from '../model/user.model';
import { LoginDetails } from '../model/login-details.model';
import {
	loginRequest,
	loginSuccess,
	loginFailure,
	keepAliveRequest,
	keepAliveSuccess,
	keepAliveFailure,
	cacheFetch,
	UserLoginData,
	UserLoginCacheData
} from './user.actions';
import {
	StateData,
	getGenericPayload,
	getRequestSuccess,
	getRequestFailure
} from 'aoede-client-generic';
import { UserState } from './user.reducer';
import { UserService } from '../user.service';
import { getAuthentication, getAuthToken, isStateAuthenticated } from './user.selectors';
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

		this.store.dispatch (cacheFetch({cache : userState}));
	}

	login$ = createEffect(
		() => this.actions$.pipe(
			ofType(loginRequest),
			switchMap((details) => this.service.login(details.payload).pipe(
				map(resp => {
					this.router.navigate(['']);
					return loginSuccess(
						getRequestSuccess(details, {
							user : resp.body!!,
							token: resp.headers.get(this.config.authToken!!)!!
						})
					)
				}),
				catchError(err => of(keepAliveFailure(
					getRequestFailure(details, err.error)
				)))
			))
		)
	);

	keepAlive$ = createEffect(
		() => this.actions$.pipe(
			ofType(keepAliveRequest),
			concatLatestFrom(() => this.store.select(getAuthToken)),
			filter(([, token]) => token !== null),
			switchMap(([details, token]) => {
				return this.service.keepAlive(token!!).pipe(
					map(resp => {
						return keepAliveSuccess(
							getRequestSuccess(details, {
								user : resp.body!!,
								token: resp.headers.get(this.config.authToken!!)!!
							})
						);
					}),
					catchError(err => of(keepAliveFailure(
						getRequestFailure(details, err.error)
					)))
				);
			})
		)
	);

	@Effect()
	keepAliveDispatcher$ = interval(this.config.tokenKeepAlive).pipe(
		mapTo(keepAliveRequest(getGenericPayload()))
	);

	updateCache$ = createEffect(
		() => this.store.select (getAuthentication).pipe(
			// ignore first item from the state
			// otherwise it will fire up first with the initial state
			// clearing up the state
			skip(1),
			map((state : StateData<UserLoginData>) : [boolean, UserLoginCacheData] => {
				var authenticated : boolean = isStateAuthenticated(state);
				var data : UserLoginCacheData = {
					user  : {
						username : "",
						status   : ""
					},
					token : "",
					time  : 0
				};

				if (authenticated && state.value !== null) {
					data = {
						user  : state.value.user,
						token : state.value.token,
						time  : state.utime
					}
				}
				return [authenticated, data];
			}),
			tap(([authenticated, data]) => {
				if (authenticated) {
					localStorage.setItem(this.cacheKey, JSON.stringify(data));
				} else {
					localStorage.removeItem(this.cacheKey);
				}
			})
		),
		{ dispatch: false }
	);

}
