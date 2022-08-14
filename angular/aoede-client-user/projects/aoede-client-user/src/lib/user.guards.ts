import { Injectable, Inject } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot,RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';
import { take, tap } from 'rxjs/operators';

import { UserState } from './store/user.reducer';
import { isAuthenticated, isAnonymous } from './store/user.selectors';
import { UserConfig, UserConfigToken } from './user.config';

@Injectable()
export class UserAuthGuard implements CanActivate {

	constructor (
		private store  : Store<UserState>,
		private router : Router,
		@Inject(UserConfigToken) private config : UserConfig
	) { }

	canActivate(
		route: ActivatedRouteSnapshot,
		state: RouterStateSnapshot
	): Observable<boolean> {
		return this.store.select (isAuthenticated).pipe (
			take(1),
			tap (authenticated => {
				if (!authenticated) {
					this.router.navigate(this.config.redirectLogin || []);
				}
			})
		);
	}

}

@Injectable()
export class AnonymousUserGuard implements CanActivate {

	constructor (
		private store  : Store<UserState>,
		private router : Router
	) { }

	canActivate(
		route: ActivatedRouteSnapshot,
		state: RouterStateSnapshot
	): Observable<boolean> {
		return this.store.select (isAnonymous);
	}

}
