import { Note } from './note.model';

export interface Measure {
	notes : Note[];
};

export const measureInitializer : Measure = {
	notes : []
};
