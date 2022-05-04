import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Store } from '@ngrx/store';

import { loginRequest } from '../store/user.actions';
import { UserState } from '../store/user.reducer';

@Component({
	selector: 'aoede-user-login',
	templateUrl: './user-login.component.html',
	styleUrls: ['./user-login.component.scss']
})
export class UserLoginComponent implements OnInit {

	public username : FormControl = new FormControl('');
	public password : FormControl = new FormControl('');

	public usernameError : string = '';
	public passwordError : string = '';

	constructor(
		private store : Store<UserState>
	) {
	}

	ngOnInit(): void {
	}

	onSubmit (): void {
		this.store.dispatch (loginRequest({
			payload: {
				username : this.username.value,
				password : this.password.value
			}
		}));
	}
}
