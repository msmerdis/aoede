import { Measure } from './measure.model';
import { Fraction } from './fraction.model';

export interface Section {
	id            : string;
	measures      : Measure[];
	sheetId       : number;
	tempo         : number;
	timeSignature : Fraction;
	trackid       : string;
}
