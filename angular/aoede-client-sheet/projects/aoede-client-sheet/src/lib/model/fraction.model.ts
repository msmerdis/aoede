export interface Fraction {
	numerator   : number;
	denominator : number;
};

export function fractionInitializer (num : number = 0, den : number = 0) : Fraction {
	return {
		numerator   : num,
		denominator : den
	};
};
