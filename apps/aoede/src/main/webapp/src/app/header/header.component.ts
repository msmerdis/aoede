import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';

import { UserState } from '../aoede/user/store/user.reducer';
import { keepAliveRequest } from '../aoede/user/store/user.actions';
import { isAuthenticated, getUsername } from '../aoede/user/store/user.selectors';

@Component({
	selector: 'app-header',
	templateUrl: './header.component.html',
	styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

	loggedin$ : Observable<boolean>;
	username$ : Observable<string>;

	constructor(
		private store : Store<UserState>
	) {
		this.loggedin$ = this.store.select (isAuthenticated);
		this.username$ = this.store.select (getUsername);
	}

	ngOnInit(): void {
	}

	public refreshToken () {
		this.store.dispatch(keepAliveRequest());
	}
}
