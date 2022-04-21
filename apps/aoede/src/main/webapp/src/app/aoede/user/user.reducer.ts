import { Action, createReducer, on } from '@ngrx/store';
import {
	loginRequest,
	loginSuccess,
	loginFailure
} from './user.actions';
import { User } from './model/user.model';

export interface UserState {
	authenticated : boolean;
	authTimestamp : string;
	authToken     : string;
	user          : User;
}

export const userFeatureKey : string = 'aoedeUserState';
export const userInitialState : UserState = {
	authenticated : false,
	authTimestamp : "",
	authToken     : "",
	user : {
		username : "empty",
		status   : ""
	}
};

export const userReducer = createReducer (
	userInitialState,
	on(loginRequest, (state, details) => ({...state, user : {...state.user, username : details.username}})),
	on(loginSuccess, (state) => state),
	on(loginFailure, (state) => state)
);

export function reducer(state: UserState, action: Action): UserState {
	return userReducer(state, action);
}

