import { TimeSignature } from './time-signature.model';
import { Note } from './note.model';

export interface Measure {
	clef?          : string;
	tempo?         : number;
	keySignature?  : number;
	timeSignature? : TimeSignature;
	notes          : Note[];
};

export function measureInitializer () : Measure {
	return {
		notes : []
	};
};
