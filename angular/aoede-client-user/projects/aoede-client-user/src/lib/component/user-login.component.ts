import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Store } from '@ngrx/store';
import { Subscription } from 'rxjs';

import { loginRequest } from '../store/user.actions';
import { UserState } from '../store/user.reducer';
import { getAuthError } from '../store/user.selectors';
import { LoginDetails } from '../model/login-details.model';
import { ApiError, ApiValidation } from 'aoede-client-generic';
import { getRequestPayload } from 'aoede-client-generic';

@Component({
	selector: 'aoede-user-login',
	templateUrl: './user-login.component.html',
	styleUrls: ['./user-login.component.scss']
})
export class UserLoginComponent implements OnInit, OnDestroy {

	public username : FormControl = new FormControl('');
	public password : FormControl = new FormControl('');

	public usernameError : string = '';
	public passwordError : string = '';

	private errors : Subscription = Subscription.EMPTY;

	constructor(
		private store : Store<UserState>
	) {}

	ngOnInit(): void {
		this.errors = this.store.select(getAuthError).subscribe (
			(errors : ApiError | null) => {
				this.usernameError = "";
				this.passwordError = "";

				if (errors == null || errors.validations === undefined)
					return;

				errors.validations.forEach ((validation : ApiValidation) => {
					if (validation.field == "username") this.usernameError = validation.error;
					if (validation.field == "password") this.passwordError = validation.error;
				});
			}
		);
	}

	ngOnDestroy(): void {
		this.errors.unsubscribe ();
	}

	onSubmit (): void {
		this.store.dispatch (loginRequest(
			getRequestPayload<LoginDetails>({
				username : this.username.value,
				password : this.password.value
			})
		));
	}
}
