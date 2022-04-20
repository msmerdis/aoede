import { createAction, props } from '@ngrx/store';

import { Sheet } from '../model/sheet.model';

export const fetchSheetRequest = createAction('[Music] Fetch Sheet Request', props<{payload: {sheetId: number;}}>());
export const fetchSheetSuccess = createAction('[Music] Fetch Sheet Success', props<{payload: {sheet: Sheet;}}>());
export const fetchSheetFailure = createAction('[Music] Fetch Sheet Failure', props<{payload: {code: number; text: string; desc: string;}}>());

export const fetchSheetListRequest = createAction('[Music] Fetch Sheet List Request');
export const fetchSheetListSuccess = createAction('[Music] Fetch Sheet List Success', props<{payload: {list: Sheet[];}}>());
export const fetchSheetListFailure = createAction('[Music] Fetch Sheet List Failure', props<{payload: {code: number; text: string; desc: string;}}>());
