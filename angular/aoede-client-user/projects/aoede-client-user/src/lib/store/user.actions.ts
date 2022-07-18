import { createAction, props } from '@ngrx/store';

import { User } from '../model/user.model';
import { LoginDetails } from '../model/login-details.model';
import { ApiError } from 'aoede-client-generic';
import {
	ApiGeneric,
	ApiRequest,
	ApiSuccess,
	ApiFailure
} from 'aoede-client-generic';

export interface UserLoginData {
	token : string;
	user  : User;
}

export interface UserLoginCacheData extends UserLoginData {
	time : number;
}

export const resetRequest = createAction('[User] Reset Request');
export const loginRequest = createAction('[User] Login Request', props<ApiRequest<LoginDetails>>());
export const loginSuccess = createAction('[User] Login Success', props<ApiSuccess<UserLoginData>>());
export const loginFailure = createAction('[User] Login Failure', props<ApiFailure>());

export const keepAliveRequest = createAction('[User] KeepAlive Request', props<ApiGeneric>());
export const keepAliveSuccess = createAction('[User] KeepAlive Success', props<ApiSuccess<UserLoginData>>());
export const keepAliveFailure = createAction('[User] KeepAlive Failure', props<ApiFailure>());

export const cacheFetch = createAction('[User] Cache Fetch', props<{cache : UserLoginCacheData}>());
export const tokenRenew = createAction('[User] Token Renew', props<{token : string; utime : number}>());
