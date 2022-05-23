import { createFeatureSelector, createSelector } from '@ngrx/store';

import { MusicState, musicFeatureKey } from './music.reducer';
import { Sheet } from '../model/sheet.model';

export const getMusicState = createFeatureSelector<MusicState>(musicFeatureKey);

export const getSheet     = createSelector(getMusicState, (state: MusicState): Sheet   | null => state.sheet);
export const getSheetList = createSelector(getMusicState, (state: MusicState): Sheet[] | null => state.sheetList);
