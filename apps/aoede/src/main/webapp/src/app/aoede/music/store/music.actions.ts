import { createAction, props } from '@ngrx/store';

import { Sheet } from '../model/sheet.model';
import {
	ApiGeneric,
	ApiRequest,
	ApiSuccess,
	ApiFailure
} from '../../generic/generic-api.model';

export const fetchSheetRequest = createAction('[Music] Fetch Sheet Request', props<ApiRequest<number>>());
export const fetchSheetSuccess = createAction('[Music] Fetch Sheet Success', props<ApiSuccess<Sheet>>());
export const fetchSheetFailure = createAction('[Music] Fetch Sheet Failure', props<ApiFailure>());

export const fetchSheetListRequest = createAction('[Music] Fetch Sheet List Request', props<ApiGeneric>());
export const fetchSheetListSuccess = createAction('[Music] Fetch Sheet List Success', props<ApiSuccess<Sheet[]>>());
export const fetchSheetListFailure = createAction('[Music] Fetch Sheet List Failure', props<ApiFailure>());
