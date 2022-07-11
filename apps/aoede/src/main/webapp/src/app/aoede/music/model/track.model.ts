import { Clef } from './clef.model';
import { Fraction } from './fraction.model';
import { Measure } from './measure.model';

export interface Track {
	name?          : string;
	clef           : Clef;
	tempo          : number;
	keySignature?  : number;
	timeSignature? : Fraction;
	measures       : Measure[];
}
