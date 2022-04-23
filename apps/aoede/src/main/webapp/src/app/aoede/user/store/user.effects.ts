import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { of } from 'rxjs';
import { map, switchMap, mergeMap, catchError, debounceTime } from 'rxjs/operators';
import { Router } from '@angular/router';

import { User } from '../model/user.model';
import { LoginDetails } from '../model/login-details.model';
import { loginRequest, loginSuccess, loginFailure } from './user.actions';
import { UserService } from '../user.service';

@Injectable()
export class UserEffects {
	constructor (
		private actions$ : Actions,
		private service  : UserService,
		private router   : Router
	) { }

	login$ = createEffect(() => this.actions$.pipe(
		ofType(loginRequest),
		switchMap((details) =>
			this.service.login({
				username : details.username,
				password : details.password
			}).pipe(
				map(data => {
					this.router.navigate(['']);
					return loginSuccess(data);
				}),
				catchError(err => of(loginFailure(err)))
			)
		)
	));

}
