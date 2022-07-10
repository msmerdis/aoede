import { createFeatureSelector, createSelector } from '@ngrx/store';

import { MusicState, musicFeatureKey } from './music.reducer';
import { Sheet } from '../model/sheet.model';
import { ApiError } from '../../generic/generic-api.model';
import { StateData } from '../../generic/generic-store.model';


export const getMusicState = createFeatureSelector<MusicState>(musicFeatureKey);

export const getSheet          = createSelector(getMusicState, (state: MusicState): StateData<Sheet>   => state.sheet);
export const getSheetValue     = createSelector(getSheet,      (state: StateData<Sheet>  ): Sheet    | null => state.value);
export const getSheetError     = createSelector(getSheet,      (state: StateData<Sheet>  ): ApiError | null => state.error);
export const getSheetUTime     = createSelector(getSheet,      (state: StateData<Sheet>  ): number          => state.utime);
export const getSheetList      = createSelector(getMusicState, (state: MusicState): StateData<Sheet[]> => state.sheetList);
export const getSheetListValue = createSelector(getSheetList,  (state: StateData<Sheet[]>): Sheet[]  | null => state.value);
export const getSheetListError = createSelector(getSheetList,  (state: StateData<Sheet[]>): ApiError | null => state.error);
export const getSheetListUTime = createSelector(getSheetList,  (state: StateData<Sheet[]>): number          => state.utime);

export const getSheetValueSafe = createSelector(getSheetValue, (sheet: Sheet | null, id : number): Sheet | null =>
	sheet !== null && sheet.id === id ? sheet : null
);
