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
	errorList  : string  | null;
	sheet      : Sheet   | null;
	error      : string  | null;
}

export const musicFeatureKey : string = 'aoedeMusicState';
export const musicInitialState : MusicState = {
	sheetList  : null,
	errorList  : null,
	sheet      : null,
	error      : null
};

function doFetchSheetSuccess (state : MusicState, data : {success : SheetSuccess}) : MusicState {
	return {
		...state,
		sheet : data.success.sheet,
		error : null
	};
}

function doFetchSheetFailure (state : MusicState, data : {failure : SheetFailure}) : MusicState {
	return {
		...state,
		sheet : null,
		error : JSON.stringify(data.failure)
	};
}

function doFetchSheetListSuccess (state : MusicState, data : {success : SheetListSuccess}) : MusicState {
	return {
		...state,
		sheetList : data.success.sheetList,
		errorList : null
	};
}

function doFetchSheetListFailure (state : MusicState, data : {failure : SheetListFailure}) : MusicState {
	return {
		...state,
		sheetList : null,
		errorList : JSON.stringify(data.failure)
	};
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

