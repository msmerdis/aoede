import { createAction, props } from '@ngrx/store';

import { Sheet } from '../model/sheet.model';

export interface CreateSheet {
	name : string;
}

export interface SheetSuccess {
	sheet: Sheet;
}

export interface SheetFailure {
	code: number;
	text: string;
	desc: string;
}

export interface SheetListSuccess {
	sheetList: Sheet[];
}

export interface SheetListFailure extends SheetFailure {
}

export const fetchSheetRequest = createAction('[Music] Fetch Sheet Request', props<{payload: number}>());
export const fetchSheetSuccess = createAction('[Music] Fetch Sheet Success', props<{success: SheetSuccess}>());
export const fetchSheetFailure = createAction('[Music] Fetch Sheet Failure', props<{failure: SheetFailure}>());

export const fetchSheetListRequest = createAction('[Music] Fetch Sheet List Request');
export const fetchSheetListSuccess = createAction('[Music] Fetch Sheet List Success', props<{success: SheetListSuccess}>());
export const fetchSheetListFailure = createAction('[Music] Fetch Sheet List Failure', props<{failure: SheetListFailure}>());
