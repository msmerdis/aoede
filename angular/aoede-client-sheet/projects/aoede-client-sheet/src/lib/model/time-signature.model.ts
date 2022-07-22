import { Fraction, fractionInitializer } from './fraction.model';

export interface TimeSignature extends Fraction {
	beats? : number[];
};

export function timeSignatureInitializer (num : number = 0, den : number = 0) : TimeSignature {
	return fractionInitializer(num, den);
};
