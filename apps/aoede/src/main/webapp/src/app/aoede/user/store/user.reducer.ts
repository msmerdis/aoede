import { Action, createReducer, on } from '@ngrx/store';
import {
	TokenRenewInfo,
	UserLoginSuccess,
	UserLoginFailure,
	loginSuccess,
	loginFailure,
	keepAliveSuccess,
	keepAliveFailure,
	tokenRenew,
	resetRequest
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

function doLoginSuccess (state : UserState, data : {success: UserLoginSuccess}) : UserState {
	return {
		authenticated : true,
		authTimestamp : data.success.time,
		authToken     : data.success.token,
		user          : data.success.user
	}
}

function doLoginFailure (state : UserState, data : {failure: UserLoginFailure}) : UserState {
	return userInitialState;
}

function doResetRequest () : UserState {
	return userInitialState;
}

function doTokenRenew (state : UserState, data : {payload: TokenRenewInfo}) : UserState {
	return {
		...state,
		authenticated : data.payload.token !== null,
		authTimestamp : data.payload.time,
		authToken     : data.payload.token
	}
}

export const userReducer = createReducer (
	userInitialState,
	on(loginSuccess, doLoginSuccess),
	on(loginFailure, doLoginFailure),
	on(resetRequest, doResetRequest),
	on(keepAliveSuccess, doLoginSuccess),
	on(keepAliveFailure, doLoginFailure),
	on(tokenRenew, doTokenRenew),
);

export function reducer(state: UserState, action: Action): UserState {
	return userReducer(state, action);
}

