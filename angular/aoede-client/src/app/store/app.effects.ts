import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { of } from 'rxjs';
import { map, switchMap, mergeMap, catchError, debounceTime, tap, filter } from 'rxjs/operators';

import { UserControlService } from 'aoede-client-user';
import { MusicControlService } from 'aoede-client-music';

@Injectable()
export class AppEffects {
	constructor (
		private actions$     : Actions,
		private userControl  : UserControlService,
		private musicControl : MusicControlService
	) { }

	logActions$ = createEffect(
		() => this.actions$.pipe(
			tap((action : any) => {
				console.log('got action: ' + this.timestamp() + ' ' + JSON.stringify(action.type));

				if ('failure' in action) {
					this.processFailure (action.failure);
				}
			})
		),
		{ dispatch: false }
	);

	authenticationListener$ = createEffect(
		() => this.userControl.authenticated().pipe(
			filter(auth => auth === false),
			tap(auth => this.musicControl.reset())
		),
		{ dispatch: false }
	);

	private timestamp () : string {
		let now = new Date();
		return now.getHours() + ":" + now.getMinutes() + ":" + now.getSeconds();
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
