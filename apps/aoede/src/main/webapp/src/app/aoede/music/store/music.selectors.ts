import { createFeatureSelector, createSelector } from '@ngrx/store';

import { MusicState, musicFeatureKey } from './music.reducer';
import { Sheet } from '../model/sheet.model';
import { Clef } from '../model/clef.model';
import { Tempo } from '../model/tempo.model';
import { KeySignature } from '../model/key-signature.model';
import { ApiError } from '../../generic/generic-api.model';
import { StateData } from '../../generic/generic-store.model';

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

export const getClefs      = createSelector(getMusicState, (state: MusicState): StateData<Clef[]> => state.clefList);
export const getClefsReady = createSelector(getClefs,      (state: StateData<Clef[]>): boolean         => state.ready);
export const getClefsValue = createSelector(getClefs,      (state: StateData<Clef[]>): Clef[]   | null => state.value);
export const getClefsError = createSelector(getClefs,      (state: StateData<Clef[]>): ApiError | null => state.error);
export const getClefsUTime = createSelector(getClefs,      (state: StateData<Clef[]>): number          => state.utime);

function findClef (clefs : Clef[], clef : string) : Clef | null {
	return clefs.length > 0 ? clefs.find(c => c.id == clef) || null : null;
};

export const getClef = createSelector(getClefsValue, (clefs : Clef[] | null, clef : string): Clef | null => clefs === null ? null : findClef(clefs, clef));

export const getKeys      = createSelector(getMusicState, (state: MusicState): StateData<KeySignature[]> => state.keys);
export const getKeysReady = createSelector(getKeys,       (state: StateData<KeySignature[]>): boolean               => state.ready);
export const getKeysValue = createSelector(getKeys,       (state: StateData<KeySignature[]>): KeySignature[] | null => state.value);
export const getKeysError = createSelector(getKeys,       (state: StateData<KeySignature[]>): ApiError       | null => state.error);
export const getKeysUTime = createSelector(getKeys,       (state: StateData<KeySignature[]>): number                => state.utime);

function findKey (keys : KeySignature[], key : number) : KeySignature | null {
	return keys.length > 0 ? keys.find(k => k.id == key) || null : null;
};

export const getKeySignature = createSelector(getKeysValue, (keys : KeySignature[] | null, key : number): KeySignature | null => keys === null ? null : findKey(keys, key));

export const getTempos      = createSelector(getMusicState, (state: MusicState): StateData<Tempo[]> => state.tempoList);
export const getTemposReady = createSelector(getTempos,     (state: StateData<Tempo[]>): boolean         => state.ready);
export const getTemposValue = createSelector(getTempos,     (state: StateData<Tempo[]>): Tempo[]  | null => state.value);
export const getTemposError = createSelector(getTempos,     (state: StateData<Tempo[]>): ApiError | null => state.error);
export const getTemposUTime = createSelector(getTempos,     (state: StateData<Tempo[]>): number          => state.utime);

function findTempo (tempos : Tempo[], tempo : string) : Tempo | null {
	return tempos.length > 0 ? tempos.find(t => t.id == tempo) || null : null;
};

export const getTempo = createSelector(getTemposValue, (tempos : Tempo[] | null, tempo : string): Tempo | null => tempos === null ? null : findTempo(tempos, tempo));
