import { Action, createReducer, on } from '@ngrx/store';
import {
	loginRequest,
	loginSuccess,
	loginFailure
} from './user.actions';
import { User } from '../model/user.model';
import { LoginDetails } from '../model/login-details.model';

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
		username : "",
		status   : ""
	}
};

function doLoginRequest (state : UserState, details : LoginDetails) : UserState {
	return {
		...state,
		user : {
			...state.user,
			username : details.username
		}
	}
}

function doLoginSuccess (state : UserState, user : User) : UserState {
	return state;
}

function doLoginFailure (state : UserState, failure : {code: number; text: string; desc: string;}) : UserState {
	return state;
}

export const userReducer = createReducer (
	userInitialState,
	on(loginRequest, doLoginRequest),
	on(loginSuccess, doLoginSuccess),
	on(loginFailure, doLoginFailure)
);

export function reducer(state: UserState, action: Action): UserState {
	return userReducer(state, action);
}

