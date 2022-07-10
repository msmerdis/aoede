import { Action, createReducer, on } from '@ngrx/store';
import {
	fetchSheetRequest,
	fetchSheetSuccess,
	fetchSheetFailure,
	fetchSheetListRequest,
	fetchSheetListSuccess,
	fetchSheetListFailure
} from './music.actions';
import { Sheet } from '../model/sheet.model';
import {
	ApiGeneric,
	ApiRequest,
	ApiSuccess,
	ApiFailure,
	ApiError
} from '../../generic/generic-api.model';
import {
	StateData,
	initialStateData,
	getSuccessStateData,
	getFailureStateData
} from '../../generic/generic-store.model';

export interface MusicState {
	sheet     : StateData<Sheet>;
	sheetList : StateData<Sheet[]>;
}

export const musicFeatureKey : string = 'aoedeMusicState';
export const musicInitialState : MusicState = {
	sheet     : initialStateData,
	sheetList : initialStateData
};

function doFetchSheetSuccess (state : MusicState, data : ApiSuccess<Sheet>) : MusicState {
	return {
		...state,
		sheet : getSuccessStateData (data)
	};
}

function doFetchSheetFailure (state : MusicState, data : ApiFailure) : MusicState {
	return {
		...state,
		sheet : getFailureStateData (data)
	};
}

function doFetchSheetListSuccess (state : MusicState, data : ApiSuccess<Sheet[]>) : MusicState {
	return {
		...state,
		sheetList : getSuccessStateData (data)
	};
}

function doFetchSheetListFailure (state : MusicState, data : ApiFailure) : MusicState {
	return {
		...state,
		sheetList : getFailureStateData (data)
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
