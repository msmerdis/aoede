import { Action, createReducer, on } from '@ngrx/store';
import {
	fetchSheetSuccess,
	fetchSheetFailure,
	fetchSheetListSuccess,
	fetchSheetListFailure,
	preloadSuccess,
	preloadFailure,
	generateSheetSuccess,
	generateSheetFailure,
	resetMusicModule
} from './music.actions';
import {
	Sheet,
	Clef,
	KeySignature,
	Tempo,
} from 'aoede-client-sheet';
import { Preload } from '../model/preload.model';
import {
	ApiSuccess,
	ApiFailure
} from 'aoede-client-generic';
import {
	StateData,
	initialStateData,
	getSuccessStateData,
	getFailureStateData,
	getRequestSuccess,
	appendStateData
} from 'aoede-client-generic';

export interface MusicState {
	sheet     : StateData<Sheet>;
	sheetList : StateData<Sheet[]>;
	preload   : StateData<Preload>;
}

export const musicFeatureKey : string = 'aoedeMusicState';
export const musicInitialState : MusicState = {
	sheet     : initialStateData,
	sheetList : initialStateData,
	preload   : initialStateData,
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
		preload : getSuccessStateData (data)
	};
}

function doPreloadFailure (state : MusicState, data : ApiFailure) : MusicState {
	return {
		...state,
		preload : getFailureStateData (data)
	};
}

function doGenerateSheetSuccess (state : MusicState, data : ApiSuccess<Sheet>) : MusicState {
	return {
		...state,
		sheetList : appendStateData(state.sheetList, data),
		sheet     : getSuccessStateData (data)
	};
}

function doGenerateSheetFailure (state : MusicState, data : ApiFailure) : MusicState {
	return {
		...state,
		sheet : getFailureStateData (data)
	};
}

function doResetMusicModule (state : MusicState) : MusicState {
	return {
		...state,
		sheetList : initialStateData,
		sheet     : initialStateData
	}
}

export const musicReducer = createReducer (
	musicInitialState,
	on(fetchSheetSuccess, doFetchSheetSuccess),
	on(fetchSheetFailure, doFetchSheetFailure),
	on(fetchSheetListSuccess, doFetchSheetListSuccess),
	on(fetchSheetListFailure, doFetchSheetListFailure),
	on(preloadSuccess, doPreloadSuccess),
	on(preloadFailure, doPreloadFailure),
	on(generateSheetSuccess, doGenerateSheetSuccess),
	on(generateSheetFailure, doGenerateSheetFailure),
	on(resetMusicModule, doResetMusicModule),
);

export function reducer(state: MusicState, action: Action): MusicState {
	return musicReducer(state, action);
}
