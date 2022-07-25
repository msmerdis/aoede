import { TimeSignature } from './time-signature.model';

export interface StaveSignature {
	clef?          : string;
	tempo?         : number;
	keySignature?  : number;
	timeSignature? : TimeSignature;
};

export function staveSignatureInitializer () : StaveSignature {
	return {};
};
