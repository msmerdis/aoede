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
	authTimestamp : number;
	authToken     : string;
	user          : User;
}

export const userFeatureKey : string = 'aoedeUserState';
export const userInitialState : UserState = {
	authenticated : false,
	authTimestamp : 0,
	authToken     : "",
	user : {
		username : "",
		status   : ""
	}
};

function doLoginRequest (state : UserState, details : {payload: LoginDetails}) : UserState {
	return state;
}

function doLoginSuccess (state : UserState, user : {success: User}) : UserState {
	return {
		authenticated : true,
		authTimestamp : Date.now(),
		authToken     : "todo",
		user          : user.success
	}
}

function doLoginFailure (state : UserState, data : {failure: {code: number; text: string; desc: string;}}) : UserState {
	return userInitialState;
}

export const userReducer = createReducer (
	userInitialState,
	on(loginSuccess, doLoginSuccess),
	on(loginFailure, doLoginFailure)
);

export function reducer(state: UserState, action: Action): UserState {
	return userReducer(state, action);
}

