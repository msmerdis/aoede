export interface Fraction {
	numerator   : number;
	denominator : number;
};

export function fractionInitializer () : Fraction {
	return {
		numerator   : 0,
		denominator : 0
	};
};
