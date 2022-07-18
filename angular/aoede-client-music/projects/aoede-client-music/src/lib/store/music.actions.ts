import { createAction, props } from '@ngrx/store';

import { Sheet } from 'aoede-client-sheet';
import { Preload } from '../model/preload.model';
import {
	ApiGeneric,
	ApiRequest,
	ApiSuccess,
	ApiFailure
} from 'aoede-client-generic';

export const musicEffectsInit = createAction('[Music] Effects Init');

export const fetchSheetRequest = createAction('[Music] Fetch Sheet Request', props<ApiRequest<number>>());
export const fetchSheetSuccess = createAction('[Music] Fetch Sheet Success', props<ApiSuccess<Sheet>>());
export const fetchSheetFailure = createAction('[Music] Fetch Sheet Failure', props<ApiFailure>());

export const fetchSheetListRequest = createAction('[Music] Fetch Sheet List Request', props<ApiGeneric>());
export const fetchSheetListSuccess = createAction('[Music] Fetch Sheet List Success', props<ApiSuccess<Sheet[]>>());
export const fetchSheetListFailure = createAction('[Music] Fetch Sheet List Failure', props<ApiFailure>());

export const preloadRequest = createAction('[Music] Preload Request', props<ApiGeneric>());
export const preloadSuccess = createAction('[Music] Preload Success', props<ApiSuccess<Preload>>());
export const preloadFailure = createAction('[Music] Preload Failure', props<ApiFailure>());
