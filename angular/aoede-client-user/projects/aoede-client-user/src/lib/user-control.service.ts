import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';

import { isAuthenticated } from './store/user.selectors';
import { UserState } from './store/user.reducer';

@Injectable()
export class UserControlService {

	constructor(
		private store : Store<UserState>
	) {}

	public authenticated () : Observable<boolean> {
		return this.store.select(isAuthenticated);
	}

}
