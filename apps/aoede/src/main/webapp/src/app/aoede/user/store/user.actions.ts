import { createAction, props } from '@ngrx/store';

import { User } from '../model/user.model';
import { LoginDetails } from '../model/login-details.model';

export interface UserLoginSuccess {
	user  : User;
	token : string;
	time  : number;
}

export interface UserLoginFailure {
	code: number;
	text: string;
	desc: string;
}

export const resetRequest = createAction('[User] Reset Request');
export const loginRequest = createAction('[User] Login Request', props<{payload: LoginDetails}>());
export const loginSuccess = createAction('[User] Login Success', props<{success: UserLoginSuccess}>());
export const loginFailure = createAction('[User] Login Failure', props<{failure: UserLoginFailure}>());

export const keepAliveRequest = createAction('[User] KeepAlive Request');
export const keepAliveSuccess = createAction('[User] KeepAlive Success', props<{success: UserLoginSuccess}>());
export const keepAliveFailure = createAction('[User] KeepAlive Failure', props<{failure: UserLoginFailure}>());
