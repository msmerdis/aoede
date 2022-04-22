import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { of } from 'rxjs';
import { map, switchMap, mergeMap, catchError, debounceTime } from 'rxjs/operators';

import { User } from '../model/user.model';
import { LoginDetails } from '../model/login-details.model';
import { loginRequest, loginSuccess, loginFailure } from './user.actions';

@Injectable()
export class UserEffects {
	constructor (
		private actions$ : Actions
	) { }

	login$ = createEffect(() => this.actions$.pipe(
		ofType(loginRequest),
		switchMap(() => 
		map(data => loginSuccess())
	);

}
