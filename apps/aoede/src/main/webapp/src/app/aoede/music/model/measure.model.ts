import { Note } from './note.model';

export interface Measure {
	id        : string;
	notes     : Note[];
	sectionId : string;
	sheetId   : number;
	trackId   : string;
}
