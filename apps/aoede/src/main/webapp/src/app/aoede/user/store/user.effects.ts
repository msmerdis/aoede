import { Injectable, Inject } from '@angular/core';
import { Store } from '@ngrx/store';
import { Actions, createEffect, ofType, concatLatestFrom } from '@ngrx/effects';
import { Observable, of } from 'rxjs';
import { tap, map, switchMap, catchError } from 'rxjs/operators';
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

	private name : string  = 'aoede-user-state';
	private load : boolean = false;

	constructor (
		private store    : Store<UserState>,
		private actions$ : Actions,
		private service  : UserService,
		private router   : Router,
		@Inject(UserConfigToken) private config : UserConfig
	) {
		let state = localStorage.getItem(this.name);

		console.log('state in local storage is : ' + JSON.stringify(state));
		// login from cache if data are there
		if (state !== null) {
			this.store.dispatch (loginSuccess({
				success: JSON.parse(state)
			}));
		}

		this.load = true;
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
				)
			)
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
						this.router.navigate(['']);
						return keepAliveSuccess({
							success: {
								user: resp.body!!,
								token: resp.headers.get(this.config.authToken!!)!!,
								time: timestamp
							}
						});
					}),
					catchError(err => of(keepAliveFailure({failure: err})))
				)
			)
		)
	);

	updateCache$ = createEffect(
		() => this.store.select (getUserState).pipe(
			tap((state : UserState) => {
				if (state.authenticated) {
					localStorage.setItem(this.name, JSON.stringify({
						user  : state.user,
						token : state.authToken,
						time  : state.authTimestamp
					}));
				} else if (this.load) {
					// only remove item from local store when load is set to true
					// otherwise it will fire up first with the initial state
					// clearing up the state
					localStorage.removeItem(this.name);
				}
			})
		),
		{ dispatch: false }
	);

}
