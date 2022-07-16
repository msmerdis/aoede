import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpResponse, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { User } from './model/user.model';
import { LoginDetails } from './model/login-details.model';
import { UserConfig, UserConfigToken } from './user.config';

@Injectable()
export class UserService {

	constructor(
		private httpClient: HttpClient,
		@Inject(UserConfigToken) private config : UserConfig
	) {}

	private loginUrl : string = '/login';

	public login (details : LoginDetails) : Observable<HttpResponse<User>> {
		return this.httpClient.post<User>(this.loginUrl, details, { observe: 'response' })
	}

	public keepAlive (token : string) : Observable<HttpResponse<User>> {
		return this.httpClient.get<User>(this.loginUrl, {
			headers: new HttpHeaders({
				[this.config.authToken!!]: token
			}),
			observe: 'response'
		});
	}

	public isUserRequest (url : string) : boolean {
		return url == this.loginUrl;
	}

	public shouldRenewToken(timestamp : number, current : number) : boolean {
		return  (timestamp + this.config.tokenExpiry!!) > current &&
				(timestamp + this.config.tokenRenew!!) <= current;
	}
}

