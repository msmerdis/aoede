import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { of } from 'rxjs';
import { map, switchMap, mergeMap, catchError, debounceTime, tap } from 'rxjs/operators';

@Injectable()
export class AppEffects {
	constructor (
		private actions$ : Actions
	) { }

	logActions$ = createEffect(
		() => this.actions$.pipe(
			tap((action : any) => {
				console.log('got action: ' + JSON.stringify(action.type))

				if ('failure' in action) {
					this.processFailure (action.failure);
				}
			})
		),
		{ dispatch: false }
	);

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
