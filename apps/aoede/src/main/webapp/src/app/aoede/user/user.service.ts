import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { User } from './model/user.model';
import { LoginDetails } from './model/login-details.model';

@Injectable()
export class UserService {
	constructor(
		private httpClient: HttpClient
	) {}

	public login (details : LoginDetails) : Observable<User> {
		return of({
			username : details.username,
			status   : "ACTIVE"
		});

		//return this.http.post<LoginDetails>(this.url, details);
	}
}

