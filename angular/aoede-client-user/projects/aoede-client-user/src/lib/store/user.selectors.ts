import { createFeatureSelector, createSelector } from '@ngrx/store';

import { UserLoginData } from './user.actions';
import { UserState, userFeatureKey } from './user.reducer';
import { User } from '../model/user.model';
import { ApiError } from 'aoede-client-generic';
import { StateData } from 'aoede-client-generic';

export function isStateAuthenticated (state : StateData<UserLoginData>) : boolean {
	return state.ready && state.value !== null && state.error === null && state.value.token != "";
}

export const getUserState = createFeatureSelector<UserState>(userFeatureKey);

export const getAuthentication = createSelector(getUserState, (state: UserState): StateData<UserLoginData> => state.auth);
export const getAuthValue      = createSelector(getAuthentication, (state: StateData<UserLoginData>): UserLoginData | null => state.value);
export const getAuthError      = createSelector(getAuthentication, (state: StateData<UserLoginData>): ApiError      | null => state.error);
export const getAuthTimestamp  = createSelector(getAuthentication, (state: StateData<UserLoginData>): number               => state.utime);

export const getAuthToken      = createSelector(getAuthValue, (state: UserLoginData | null): string | null => state === null ? null : state.token);
export const getUser           = createSelector(getAuthValue, (state: UserLoginData | null): User   | null => state === null ? null : state.user);
export const getUsername       = createSelector(getUser,      (state: User | null): string | null => state === null ? null : state.username);
export const getUserStatus     = createSelector(getUser,      (state: User | null): string | null => state === null ? null : state.status);

export const isAuthenticated   = createSelector(getAuthentication, (state: StateData<UserLoginData>): boolean => isStateAuthenticated(state));
export const isAnonymous       = createSelector(getAuthentication, (state: StateData<UserLoginData>): boolean => isStateAuthenticated(state) != true);
