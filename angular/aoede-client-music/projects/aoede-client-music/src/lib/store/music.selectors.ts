import { createFeatureSelector, createSelector } from '@ngrx/store';

import { MusicState, musicFeatureKey } from './music.reducer';
import {
	Sheet,
	Clef,
	Tempo,
	Fraction,
	KeySignature,
	TimeSignature
} from 'aoede-client-sheet';
import {
	ApiError,
	StateData
} from 'aoede-client-generic';
import { Preload } from '../model/preload.model';

export const getMusicState = createFeatureSelector<MusicState>(musicFeatureKey);

export const getSheet      = createSelector(getMusicState, (state: MusicState): StateData<Sheet> => state.sheet);
export const getSheetReady = createSelector(getSheet,      (state: StateData<Sheet>): boolean         => state.ready);
export const getSheetValue = createSelector(getSheet,      (state: StateData<Sheet>): Sheet    | null => state.value);
export const getSheetError = createSelector(getSheet,      (state: StateData<Sheet>): ApiError | null => state.error);
export const getSheetUTime = createSelector(getSheet,      (state: StateData<Sheet>): number          => state.utime);

export const getSheetValueSafe = createSelector(getSheetValue, (sheet: Sheet | null, id : number): Sheet | null =>
	sheet !== null && sheet.id === id ? sheet : null
);

export const getSheetList      = createSelector(getMusicState, (state: MusicState): StateData<Sheet[]> => state.sheetList);
export const getSheetListReady = createSelector(getSheetList,  (state: StateData<Sheet[]>): boolean         => state.ready);
export const getSheetListValue = createSelector(getSheetList,  (state: StateData<Sheet[]>): Sheet[]  | null => state.value);
export const getSheetListError = createSelector(getSheetList,  (state: StateData<Sheet[]>): ApiError | null => state.error);
export const getSheetListUTime = createSelector(getSheetList,  (state: StateData<Sheet[]>): number          => state.utime);

export const getPreloadData  = createSelector(getMusicState,  (state: MusicState): StateData<Preload> => state.preload);
export const getPreloadReady = createSelector(getPreloadData, (state: StateData<Preload>): boolean         => state.ready);
export const getPreloadValue = createSelector(getPreloadData, (state: StateData<Preload>): Preload  | null => state.value);
export const getPreloadError = createSelector(getPreloadData, (state: StateData<Preload>): ApiError | null => state.error);
export const getPreloadUTime = createSelector(getPreloadData, (state: StateData<Preload>): number          => state.utime);

function findClef (clefs : Clef[], clef : string) : Clef | null {
	return clefs.length > 0 ? clefs.find(c => c.id == clef) || null : null;
};

export const getClefs = createSelector(getPreloadValue, (data  : Preload | null               ): Clef[] | null => data  === null ? null : data.clefList);
export const getClef  = createSelector(getClefs,        (clefs : Clef[]  | null, clef : string): Clef   | null => clefs === null ? null : findClef(clefs, clef));

function findKey (keys : KeySignature[], key : number) : KeySignature | null {
	return keys.length > 0 ? keys.find(k => k.id == key) || null : null;
};

export const getKeys = createSelector(getPreloadValue, (data : Preload        | null              ): KeySignature[] | null => data === null ? null : data.keysList);
export const getKey  = createSelector(getKeys,         (keys : KeySignature[] | null, key : number): KeySignature   | null => keys === null ? null : findKey(keys, key));

function findTempo (tempos : Tempo[], tempo : string) : Tempo | null {
	return tempos.length > 0 ? tempos.find(t => t.id == tempo) || null : null;
};

export const getTempos = createSelector(getPreloadValue, (data   : Preload | null                ): Tempo[] | null => data   === null ? null : data.tempoList);
export const getTempo  = createSelector(getTempos,       (tempos : Tempo[] | null, tempo : string): Tempo   | null => tempos === null ? null : findTempo(tempos, tempo));

function findTime (times : TimeSignature[], num : number, den : number) : TimeSignature | null {
	return times.length > 0 ? times.find(t => t.numerator == num && t.denominator == den) || null : null;
};

function selectorTimes (data : Preload | null): TimeSignature[] | null {
	return data  === null ? null : data.timeList;
};

function selectorTime(times  : TimeSignature[] | null, fraction : Fraction): TimeSignature   | null {
	return times === null ? null : findTime(times, fraction.numerator, fraction.denominator);
};

export const getTimes = createSelector(getPreloadValue, selectorTimes);
export const getTime  = createSelector(getTimes,        selectorTime);
