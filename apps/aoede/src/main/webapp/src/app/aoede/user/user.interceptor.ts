import { Injectable, Inject } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';
import { Observable, combineLatest, map, tap, switchMap, skip, take, of } from 'rxjs';
import { Store } from '@ngrx/store';

import { UserState } from './store/user.reducer';
import { UserService } from './user.service';
import { keepAliveRequest } from './store/user.actions';
import { getAuthToken, getAuthTimestamp } from './store/user.selectors';
import { UserConfig, UserConfigToken } from './user.config';

@Injectable()
export class UserInterceptor implements HttpInterceptor {

	constructor (
		private store       : Store<UserState>,
		private userService : UserService,
		@Inject(UserConfigToken) private config : UserConfig
	) { }

	public shouldRefreshToken(timestamp : number, current : number) : boolean {
		return  timestamp + this.config.tokenExpiry!!  > current &&
				timestamp + this.config.tokenRefresh!! < current;
	}

	public intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
		if (this.userService.isUserRequest(req.url)) {
			console.log("skip user requests");
			return next.handle(req);
		}

		return combineLatest(
			this.store.select(getAuthToken),
			this.store.select(getAuthTimestamp),
			(token, timestamp) => {
				return {
					token     : token,
					timestamp : timestamp,
					current   : Date.now()
				};
			}
		)
		.pipe (
			// check if an update on the authentication data should occure
			map ((auth) => {
				return {
					...auth,
					refresh : auth.token != "" && this.shouldRefreshToken(auth.timestamp, auth.current)
				};
			}),

			// in case data need to be updated, dispatch the action
			tap ((auth) => auth.refresh && this.store.dispatch(keepAliveRequest())),

			// select the correct token to use
			switchMap((auth) => {
				if (auth.refresh) {
					return this.store.select(getAuthToken).pipe(skip(1));
				}
				return of (auth.token);
			}),
			take(1),

			switchMap ((token) => {
				console.log("inject auth token " + token + " to " + req.url);
				const apiReq = req.clone({
					headers: req.headers.set(this.config.authToken!!, token)
				});
				return next.handle(apiReq);
			})
		);
	}

}

