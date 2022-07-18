import { Action, createReducer, on } from '@ngrx/store';
import {
	UserLoginData,
	UserLoginCacheData,
	loginSuccess,
	loginFailure,
	keepAliveSuccess,
	keepAliveFailure,
	cacheFetch,
	tokenRenew,
	resetRequest
} from './user.actions';
import { User } from '../model/user.model';
import { LoginDetails } from '../model/login-details.model';
import {
	ApiSuccess,
	ApiFailure
} from 'aoede-client-generic';
import {
	StateData,
	initialStateData,
	getSuccessStateData,
	getFailureStateData,
	getRequestSuccess
} from 'aoede-client-generic';


export interface UserState {
	auth : StateData<UserLoginData>;
}

export const userFeatureKey : string = 'aoedeUserState';
export const userInitialState : UserState = {
	auth : initialStateData
};

function doLoginSuccess (state : UserState, data : ApiSuccess<UserLoginData>) : UserState {
	return {
		...state,
		auth : getSuccessStateData (data)
	};
}

function doLoginFailure (state : UserState, data : ApiFailure) : UserState {
	return {
		...state,
		auth : getFailureStateData (data)
	};
}

function doResetRequest () : UserState {
	return userInitialState;
}

function doCacheFetch (state : UserState, data : {cache : UserLoginCacheData}) : UserState {
	return {
		...state,
		auth : {
			...state.auth,
			ready : true,
			utime : data.cache.time,
			value : {
				...state.auth.value!!,
				token : data.cache.token,
				user  : data.cache.user
			}
		}
	};
}

function doTokenRenew (state : UserState, data : {token : string; utime : number}) : UserState {
	return {
		...state,
		auth : {
			...state.auth,
			utime : data.utime,
			value : {
				...state.auth.value!!,
				token : data.token
			}
		}
	};
}


export const userReducer = createReducer (
	userInitialState,
	on(loginSuccess, doLoginSuccess),
	on(loginFailure, doLoginFailure),
	on(resetRequest, doResetRequest),
	on(keepAliveSuccess, doLoginSuccess),
	on(keepAliveFailure, doLoginFailure),
	on(cacheFetch, doCacheFetch),
	on(tokenRenew, doTokenRenew),
);

export function reducer(state: UserState, action: Action): UserState {
	return userReducer(state, action);
}

