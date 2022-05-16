import { Action, createReducer, on } from '@ngrx/store';
import {
	fetchSheetRequest,
	fetchSheetSuccess,
	fetchSheetFailure,
	fetchSheetListRequest,
	fetchSheetListSuccess,
	fetchSheetListFailure,

	SheetSuccess,
	SheetFailure,
	SheetListSuccess,
	SheetListFailure
} from './music.actions';
import { Sheet } from '../model/sheet.model';

export interface MusicState {
	sheetList  : Sheet[] | null;
	sheet      : Sheet   | null;
}

export const musicFeatureKey : string = 'aoedeMusicState';
export const musicInitialState : MusicState = {
	sheetList  : null,
	sheet      : null
};

function doFetchSheetSuccess (state : MusicState, data : {success : SheetSuccess}) : MusicState {
	return state;
}

function doFetchSheetFailure (state : MusicState, data : {failure : SheetFailure}) : MusicState {
	return state;
}

function doFetchSheetListSuccess (state : MusicState, data : {success : SheetListSuccess}) : MusicState {
	return state;
}

function doFetchSheetListFailure (state : MusicState, data : {failure : SheetListFailure}) : MusicState {
	return state;
}

export const musicReducer = createReducer (
	musicInitialState,
	on(fetchSheetSuccess, doFetchSheetSuccess),
	on(fetchSheetFailure, doFetchSheetFailure),
	on(fetchSheetListSuccess, doFetchSheetListSuccess),
	on(fetchSheetListFailure, doFetchSheetListFailure),
);

export function reducer(state: MusicState, action: Action): MusicState {
	return musicReducer(state, action);
}

