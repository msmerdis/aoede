import { StaveSignature, staveSignatureInitializer } from './stave-signature.model';

import { TimeSignature, timeSignatureBeats } from  './time-signature.model';

import {
	MappedClef,
	mappedClefInitializer,
	MappedKeySignature,
	mappedKeySignatureInitializer,
	MappedTimeSignature,
	mappedTimeSignatureInitializer
} from './stave.model';

export interface StaveMapState extends StaveSignature {
	beats      : number[];
	mappedClef : MappedClef;
	mappedKey  : MappedKeySignature;
	mappedTime : MappedTimeSignature;
};

export function staveMapStateInitializer (
	staveSignature : StaveSignature  = staveSignatureInitializer(),
	timeSignatures : TimeSignature[] = [],
	defaultTime    : number[]        = [0],
	mappedClef     : MappedClef          = mappedClefInitializer(),
	mappedKey      : MappedKeySignature  = mappedKeySignatureInitializer(),
	mappedTime     : MappedTimeSignature = mappedTimeSignatureInitializer()
) : StaveMapState {
	return {
		...staveSignatureInitializer(),
		clef          : staveSignature.clef,
		tempo         : staveSignature.tempo,
		keySignature  : staveSignature.keySignature,
		timeSignature : staveSignature.timeSignature,
		beats         : staveSignature.timeSignature ? timeSignatureBeats(staveSignature.timeSignature, timeSignatures, defaultTime) : defaultTime,
		mappedClef    : mappedClef,
		mappedKey     : mappedKey,
		mappedTime    : mappedTime
	};
};
