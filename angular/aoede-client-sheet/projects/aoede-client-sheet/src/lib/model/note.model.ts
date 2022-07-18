import { Fraction, fractionInitializer } from './fraction.model';

export interface Note {
	order : number;
	pitch : number;
	value : Fraction;
};

export const noteInitializer : Note = {
	order : -1,
	pitch : -1,
	value : fractionInitializer
};
