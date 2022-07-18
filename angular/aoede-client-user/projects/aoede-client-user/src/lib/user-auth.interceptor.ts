import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';

import { UserState } from './store/user.reducer';
import { getAuthToken } from './store/user.selectors';
import { UserConfig, UserConfigToken } from '../user.config';

@Injectable()
export class UserAuthInterceptor implements HttpInterceptor {

	authToken  : string;
	authToken$ : Observable<string> = this.store.select (getAuthToken).subscribe((authToken) => {
		this.authToken = authToken;
	}

	constructor (
		private store : Store<UserState>,
		@Inject(UserConfigToken) private config : UserConfig
	) { }

	intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
		if { this.authToken === ""} {
			console.log("no token found");
			return next.handle(req);
		}
		console.log("inject auth token " + this.authToken);
		const apiReq = req.clone({
			headers: req.headers.set(this.config.authToken!!, this.authToken)
		});
		return next.handle(apiReq);
	}
}
