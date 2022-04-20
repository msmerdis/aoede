import { createReducer, on } from '@ngrx/store';
import {
	fetchSheetRequest,
	fetchSheetSuccess,
	fetchSheetFailure,
	fetchSheetListRequest,
	fetchSheetListSuccess,
	fetchSheetListFailure
} from './sheet.actions';

export const musicSheetFeatureKey = 'aoedeMusicSheetState';
export const musicSheetState = 0;

export const musicSheetReducer = createReducer (
	musicSheetState,
	on(fetchSheetRequest, (state) => state + 1),
	on(fetchSheetSuccess, (state) => state - 1),
	on(fetchSheetFailure, (state) => 0),
	on(fetchSheetListRequest, (state) => state + 1),
	on(fetchSheetListSuccess, (state) => state - 1),
	on(fetchSheetListFailure, (state) => 0)
);
