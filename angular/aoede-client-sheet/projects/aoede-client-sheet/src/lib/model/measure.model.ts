import { StaveSignature, staveSignatureInitializer } from './stave-signature.model';
import { Note } from './note.model';

export interface Measure extends StaveSignature {
	notes  : Note[];
	tags?  : string[];
	flags? : Record<string, string>;
};

export function measureInitializer () : Measure {
	return {
		...staveSignatureInitializer(),
		notes : []
	};
};
