import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';

import { User } from './model/user.model';
import { UserState } from './user.reducer';
import { loginRequest } from './user.actions';
import { getUsername } from './user.selectors';

@Component({
	selector: 'aoede-user-login',
	templateUrl: './user-login.component.html',
	styleUrls: ['./user-login.component.scss']
})
export class UserLoginComponent implements OnInit {

	username = new FormControl('');
	password = new FormControl('');
	title$ : Observable<String>;

	constructor(
		private store : Store<UserState>
	) {
		this.title$ = this.store.select (getUsername);
	}

	ngOnInit(): void {
	}

	onSubmit (): void {
		this.store.dispatch (loginRequest({
			username : this.username.value,
			password : this.password.value
		}));
	}
}
