import { Injectable, Inject } from '@angular/core';
import { HttpEvent, HttpEventType, HttpInterceptor, HttpHandler, HttpRequest, HttpResponse, } from '@angular/common/http';
import { Observable, combineLatest, map, tap, switchMap, skip, take, of, filter } from 'rxjs';
import { Store } from '@ngrx/store';

import { UserState } from './store/user.reducer';
import { UserService } from './user.service';
import { keepAliveRequest, tokenRenew } from './store/user.actions';
import { getAuthToken, getAuthTimestamp } from './store/user.selectors';
import { UserConfig, UserConfigToken } from './user.config';
import { getGenericPayload, getRequestSuccess } from 'aoede-client-generic';

@Injectable()
export class UserInterceptor implements HttpInterceptor {

	constructor (
		private store       : Store<UserState>,
		private userService : UserService,
		@Inject(UserConfigToken) private config : UserConfig
	) { }

	public intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
		if (this.userService.isUserRequest(req.url)) {
			return next.handle(req);
		}

		return this.renewTokenDuringRequest (req, next, Date.now());
	}

	private renewTokenDuringRequest (req: HttpRequest<any>, next: HttpHandler, now : number) : Observable<HttpEvent<any>> {
		return combineLatest(
			this.store.select(getAuthToken),
			this.store.select(getAuthTimestamp),
			(token, timestamp) => {
				return {
					token : token,
					renew : token != "" && this.userService.shouldRenewToken(timestamp, now)
				};
			}
		)
		.pipe (
			take(1),
			switchMap ((auth) => {
				var headers = req.headers.set(this.config.authToken!!, auth.token || "");

				if (auth.renew) {
					headers = headers.set(this.config.renewFlag!!, "1");
				}

				var resp = next.handle(req.clone({headers: headers}));

				if (auth.renew) {
					return this.handleRenewedToken(resp, now);
				}

				return resp;
			})
		);
	}

	private handleRenewedToken (response : Observable<HttpEvent<any>>, now : number) : Observable<HttpEvent<any>> {
		return response.pipe(
			filter((httpEvent : HttpEvent<any>) => (httpEvent.type == HttpEventType.Response)),
			take(1),
			tap((httpEvent : HttpEvent<any>) => {
				if (httpEvent instanceof HttpResponse) {
					this.store.dispatch(tokenRenew({
						utime : now,
						token : httpEvent.headers.get(this.config.authToken!!)!!
					}));
				}
			})
		);
	}

	private refreshTokenBeforeRequest (req: HttpRequest<any>, next: HttpHandler, now : number) : Observable<HttpEvent<any>> {
		return combineLatest(
			this.store.select(getAuthToken),
			this.store.select(getAuthTimestamp),
			(token, timestamp) => {
				return {
					token     : token,
					timestamp : timestamp
				};
			}
		)
		.pipe (
			take(1),

			// check if an update on the authentication data should occure
			map ((auth) => {
				return {
					...auth,
					refresh : auth.token != "" && this.userService.shouldRenewToken(auth.timestamp, now)
				};
			}),

			// in case data need to be updated, dispatch the action
			tap ((auth) => auth.refresh && this.store.dispatch(keepAliveRequest(getGenericPayload()))),

			// select the correct token to use
			switchMap((auth) => {
				if (auth.refresh) {
					return this.store.select(getAuthToken).pipe(skip(1));
				}
				return of (auth.token);
			}),
			take(1),

			switchMap ((token) => {
				const apiReq = req.clone({
					headers: req.headers.set(this.config.authToken!!, token || "")
				});
				return next.handle(apiReq);
			})
		);
	}

}

