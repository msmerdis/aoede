import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot,RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';
import { first } from 'rxjs/operators';

import { UserState } from './store/user.reducer';
import { isAuthenticated, isAnonymous } from './store/user.selectors';

@Injectable()
export class UserAuthGuard implements CanActivate {

	constructor (
		private store  : Store<UserState>,
		private router : Router
	) { }

	canActivate(
		route: ActivatedRouteSnapshot,
		state: RouterStateSnapshot
	): Observable<boolean> {
		return this.store.select (isAuthenticated);
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
