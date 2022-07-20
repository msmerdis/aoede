import { Clef, clefInitializer } from './clef.model';
import { KeySignature, keySignatureInitializer } from './key-signature.model';
import { Fraction, fractionInitializer } from './fraction.model';
import { Stave } from './stave.model';

export interface TrackInfo {
	title         : string;
	name          : string;
	clef          : Clef;
	tempo         : number;
	keySignature  : KeySignature;
	timeSignature : Fraction;
	staves        : Stave[];
};

export function trackInfoInitializer () : TrackInfo {
	return {
		title         : "",
		name          : "",
		clef          : clefInitializer(),
		tempo         : 0,
		keySignature  : keySignatureInitializer(),
		timeSignature : fractionInitializer(),
		staves        : []
	};
};
