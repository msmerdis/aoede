import { createAction, props } from '@ngrx/store';

import { User } from '../model/user.model';
import { LoginDetails } from '../model/login-details.model';

export const loginRequest = createAction('[User] Login Request', props<LoginDetails>());
export const loginSuccess = createAction('[User] Login Success', props<User>());
export const loginFailure = createAction('[User] Login Failure', props<{code: number; text: string; desc: string;}>());
