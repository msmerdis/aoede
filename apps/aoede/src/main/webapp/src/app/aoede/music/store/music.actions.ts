import { createAction, props } from '@ngrx/store';

import { Sheet } from '../model/sheet.model';
import { Clef } from '../model/clef.model';
import { KeySignature } from '../model/key-signature.model';
import {
	ApiGeneric,
	ApiRequest,
	ApiSuccess,
	ApiFailure
} from '../../generic/generic-api.model';

export const musicEffectsInit = createAction('[Music] Effects Init');

export const fetchSheetRequest = createAction('[Music] Fetch Sheet Request', props<ApiRequest<number>>());
export const fetchSheetSuccess = createAction('[Music] Fetch Sheet Success', props<ApiSuccess<Sheet>>());
export const fetchSheetFailure = createAction('[Music] Fetch Sheet Failure', props<ApiFailure>());

export const fetchSheetListRequest = createAction('[Music] Fetch Sheet List Request', props<ApiGeneric>());
export const fetchSheetListSuccess = createAction('[Music] Fetch Sheet List Success', props<ApiSuccess<Sheet[]>>());
export const fetchSheetListFailure = createAction('[Music] Fetch Sheet List Failure', props<ApiFailure>());

export const fetchClefsPreload = createAction('[Music] Fetch Clefs Preload', props<ApiGeneric>());
export const fetchClefsSuccess = createAction('[Music] Fetch Clefs Success', props<ApiSuccess<Clef[]>>());
export const fetchClefsFailure = createAction('[Music] Fetch Clefs Failure', props<ApiFailure>());

export const fetchKeysPreload = createAction('[Music] Fetch Keys Preload', props<ApiGeneric>());
export const fetchKeysSuccess = createAction('[Music] Fetch Keys Success', props<ApiSuccess<KeySignature[]>>());
export const fetchKeysFailure = createAction('[Music] Fetch Keys Failure', props<ApiFailure>());
