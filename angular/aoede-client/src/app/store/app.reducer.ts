import { Action, createReducer, on } from '@ngrx/store';

export interface AppState {
}

export const initialState : AppState = {
};

export const appReducer = createReducer (
	initialState,
);

export function reducer(state: AppState, action: Action): AppState {
	return appReducer(state, action);
}

