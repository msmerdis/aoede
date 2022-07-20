import { Fraction, fractionInitializer } from './fraction.model';
import { Note } from './note.model';

export interface Measure {
	clef?          : string;
	tempo?         : number;
	keySignature?  : number;
	timeSignature? : Fraction;
	notes          : Note[];
};

export function measureInitializer () : Measure {
	return {
		notes : []
	};
};
