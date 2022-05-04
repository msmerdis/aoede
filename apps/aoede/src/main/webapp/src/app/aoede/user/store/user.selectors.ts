import { createFeatureSelector, createSelector } from '@ngrx/store';

import { UserState, userFeatureKey } from './user.reducer';
import { User } from '../model/user.model';

export const getUserState = createFeatureSelector<UserState>(userFeatureKey);

export const isAuthenticated = createSelector(getUserState, (state: UserState): boolean => state.authenticated);
export const isAnonymous     = createSelector(getUserState, (state: UserState): boolean => !state.authenticated);
export const getAuthToken    = createSelector(getUserState, (state: UserState): string => state.authToken);
export const getUser         = createSelector(getUserState, (state: UserState): User => state.user);
export const getUsername     = createSelector(getUser,      (state: User): string => state.username);
export const getUserStatus   = createSelector(getUser,      (state: User): string => state.status);
