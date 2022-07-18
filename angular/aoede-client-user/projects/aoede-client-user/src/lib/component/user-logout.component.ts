import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Store } from '@ngrx/store';

import { resetRequest } from '../store/user.actions';
import { UserState } from '../store/user.reducer';

@Component({
	selector: 'aoede-user-logout',
	template: '',
	styleUrls: []
})
export class UserLogoutComponent implements OnInit {

	constructor(
		private store : Store<UserState>
	) {
	}

	ngOnInit(): void {
		this.store.dispatch (resetRequest());
	}

}
