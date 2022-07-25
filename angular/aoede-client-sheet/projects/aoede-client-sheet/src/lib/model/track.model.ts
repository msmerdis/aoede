import { StaveSignature, staveSignatureInitializer } from './stave-signature.model';
import { Measure } from './measure.model';

export interface Track extends StaveSignature {
	name?    : string;
	measures : Measure[];
};

export function trackInitializer () : Track {
	return {
		...staveSignatureInitializer(),
		measures : []
	};
};
