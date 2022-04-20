import { Clef } from './clef.model';
import { Section } from './section.model';

export interface Track {
	clef     : Clef;
	id       : string;
	sections : Section[];
	sheetId  : number;
}
