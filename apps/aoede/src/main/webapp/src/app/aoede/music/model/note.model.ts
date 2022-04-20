import { Fraction } from './fraction.model';

export interface Note {
	id        : string;
	measureId : string;
	note      : number;
	sectionId : string;
	sheetId   : number;
	trackId   : string;
	value     : Fraction;
}
