import { createAction, props } from '@ngrx/store';

import { User } from '../model/user.model';
import { LoginDetails } from '../model/login-details.model';
import { ApiError } from '../../generic/generic-api.model';

export interface TokenRenewInfo {
	token : string;
	time  : number;
}

export interface UserLoginSuccess extends TokenRenewInfo {
	user  : User;
}

export interface UserLoginFailure extends ApiError {
}

export const resetRequest = createAction('[User] Reset Request');
export const loginRequest = createAction('[User] Login Request', props<{payload: LoginDetails}>());
export const loginSuccess = createAction('[User] Login Success', props<{success: UserLoginSuccess}>());
export const loginFailure = createAction('[User] Login Failure', props<{failure: UserLoginFailure}>());

export const keepAliveRequest = createAction('[User] KeepAlive Request');
export const keepAliveSuccess = createAction('[User] KeepAlive Success', props<{success: UserLoginSuccess}>());
export const keepAliveFailure = createAction('[User] KeepAlive Failure', props<{failure: UserLoginFailure}>());

export const tokenRenew = createAction('[User] Token Renew', props<{payload: TokenRenewInfo}>());
