import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot,RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';

import { UserState } from '../aoede/user/user.reducer';
import { isAuthenticated } from '../aoede/user/user.selectors';

@Injectable()
export class UserAuthGuard implements CanActivate {
	authenticated$ : Observable<boolean>;

	constructor (
		private store : Store<UserState>
	) {
		this.authenticated$ = this.store.select (isAuthenticated);
	}

	canActivate(
		route: ActivatedRouteSnapshot,
		state: RouterStateSnapshot
	): Observable<boolean> {
		return authenticated$;
	}

}

@Injectable()
export class AnonymousUserGuard implements CanActivate {
	authenticated$ : Observable<boolean>;

	constructor (
		private store : Store<UserState>
	) {
		this.authenticated$ = this.store.select (isAuthenticated);
	}

	canActivate(
		route: ActivatedRouteSnapshot,
		state: RouterStateSnapshot
	): Observable<boolean> {
		return authenticated$;
	}

}
