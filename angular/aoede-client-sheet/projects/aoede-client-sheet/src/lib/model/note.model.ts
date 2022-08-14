import { Fraction, fractionInitializer } from './fraction.model';

export interface Note {
	order  : number;
	pitch  : number;
	value  : Fraction;
	tags?  : string[];
	flags? : Record<string, string>;
};

export function noteInitializer () : Note {
	return {
		order : -1,
		pitch : -1,
		value : fractionInitializer()
	};
};
