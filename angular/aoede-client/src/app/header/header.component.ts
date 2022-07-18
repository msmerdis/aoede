import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';

import { UserState } from 'aoede-client-user';
import { keepAliveRequest } from 'aoede-client-user';
import { isAuthenticated, getUsername } from 'aoede-client-user';
import { getGenericPayload } from 'aoede-client-generic';


@Component({
	selector: 'app-header',
	templateUrl: './header.component.html',
	styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

	loggedin$ : Observable<boolean>;
	username$ : Observable<string | null>;

	constructor(
		private store : Store<UserState>
	) {
		this.loggedin$ = this.store.select (isAuthenticated);
		this.username$ = this.store.select (getUsername);
	}

	ngOnInit(): void {
	}

	public refreshToken () {
		this.store.dispatch(keepAliveRequest(getGenericPayload()));
	}
}
