import { Fraction } from './fraction.model';

export interface Note {
	order : number;
	pitch : number;
	value : Fraction;
}
