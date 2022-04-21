import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { loginRequest } from './user.actions';
import { UserState } from './user.reducer';

@Injectable()
export class UserService {
	constructor(
		private httpClient: HttpClient
	) {}

	login (username: string, password: string) {
		alert('login : ' + username + '/' + password);
	}
}

