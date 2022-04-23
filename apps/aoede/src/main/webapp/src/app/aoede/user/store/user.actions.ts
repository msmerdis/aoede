import { createAction, props } from '@ngrx/store';

import { User } from '../model/user.model';
import { LoginDetails } from '../model/login-details.model';

export const loginRequest = createAction('[User] Login Request', props<{payload: LoginDetails}>());
export const loginSuccess = createAction('[User] Login Success', props<{success: User}>());
export const loginFailure = createAction('[User] Login Failure', props<{failure: {code: number; text: string; desc: string;}}>());
