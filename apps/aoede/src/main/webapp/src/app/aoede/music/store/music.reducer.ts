import { Action, createReducer, on } from '@ngrx/store';
import {
	fetchSheetSuccess,
	fetchSheetFailure,
	fetchSheetListSuccess,
	fetchSheetListFailure,
	preloadSuccess,
	preloadFailure
} from './music.actions';
import { Sheet } from '../model/sheet.model';
import { Clef } from '../model/clef.model';
import { KeySignature } from '../model/key-signature.model';
import { Preload } from '../model/preload.model';
import {
	ApiSuccess,
	ApiFailure
} from '../../generic/generic-api.model';
import {
	StateData,
	initialStateData,
	getSuccessStateData,
	getFailureStateData,
	getRequestSuccess
} from '../../generic/generic-store.model';

export interface MusicState {
	sheet     : StateData<Sheet>;
	sheetList : StateData<Sheet[]>;
	clefList  : StateData<Clef[]>;
	keys      : StateData<KeySignature[]>;
}

export const musicFeatureKey : string = 'aoedeMusicState';
export const musicInitialState : MusicState = {
	sheet     : initialStateData,
	sheetList : initialStateData,
	clefList  : initialStateData,
	keys      : initialStateData
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

function doPreloadSuccess (state : MusicState, data : ApiSuccess<Preload>) : MusicState {
	return {
		...state,
		clefList : getSuccessStateData (getRequestSuccess(data, data.success.clefList)),
		keys     : getSuccessStateData (getRequestSuccess(data, data.success.keysList))
	};
}

function doPreloadFailure (state : MusicState, data : ApiFailure) : MusicState {
	return {
		...state,
		clefList : getFailureStateData (data),
		keys     : getFailureStateData (data)
	};
}

export const musicReducer = createReducer (
	musicInitialState,
	on(fetchSheetSuccess, doFetchSheetSuccess),
	on(fetchSheetFailure, doFetchSheetFailure),
	on(fetchSheetListSuccess, doFetchSheetListSuccess),
	on(fetchSheetListFailure, doFetchSheetListFailure),
	on(preloadSuccess, doPreloadSuccess),
	on(preloadFailure, doPreloadFailure),
);

export function reducer(state: MusicState, action: Action): MusicState {
	return musicReducer(state, action);
}
