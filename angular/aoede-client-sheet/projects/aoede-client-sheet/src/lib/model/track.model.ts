import { Fraction, fractionInitializer } from './fraction.model';
import { Measure } from './measure.model';

export interface Track {
	name?         : string;
	clef          : string;
	tempo         : number;
	keySignature  : number;
	timeSignature : Fraction;
	measures      : Measure[];
};

export const trackInitializer : Track = {
	clef          : "",
	tempo         : 0,
	keySignature  : 0,
	timeSignature : fractionInitializer,
	measures      : []
};
