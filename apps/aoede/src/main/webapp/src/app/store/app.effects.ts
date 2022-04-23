import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { of } from 'rxjs';
import { map, switchMap, mergeMap, catchError, debounceTime } from 'rxjs/operators';

import { loginSuccess } from '../aoede/user/store/user.actions';

@Injectable()
export class AppEffects {
	constructor (
		private actions$ : Actions
	) {
		this.actions$.subscribe (
			(a : any) => {
				console.log ('got action: ' + JSON.stringify(a.type))

				if ('failure' in a) {
					this.processFailure (a.failure);
				}
			}
		);
	}

	private processFailure (failure : any) {
		if (
			'code' in failure &&
			'text' in failure &&
			'desc' in failure
		) {
			console.log ('got failure: ' + JSON.stringify(failure))
		}
	}
}
