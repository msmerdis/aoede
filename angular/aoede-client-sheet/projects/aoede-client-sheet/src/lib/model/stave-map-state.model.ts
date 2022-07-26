import { StaveSignature, staveSignatureInitializer } from './stave-signature.model';

import { TimeSignature, timeSignatureBeats } from  '../model/time-signature.model';

export interface StaveMapState extends StaveSignature {
	beats : number[];
};

export function staveMapStateInitializer (
	staveSignature : StaveSignature = staveSignatureInitializer(),
	timeSignatures : TimeSignature[] = [],
	defaultTime    : number[] = [0]
) : StaveMapState {
	return {
		...staveSignatureInitializer(),
		clef          : staveSignature.clef,
		tempo         : staveSignature.tempo,
		keySignature  : staveSignature.keySignature,
		timeSignature : staveSignature.timeSignature,
		beats         : staveSignature.timeSignature ? timeSignatureBeats(staveSignature.timeSignature, timeSignatures, defaultTime) : defaultTime
	};
};
