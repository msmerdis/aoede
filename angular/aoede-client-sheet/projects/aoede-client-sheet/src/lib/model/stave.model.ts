import { Measure, measureInitializer } from './measure.model';
import { Note } from './note.model';

interface StaveExtention {
	offset : number;
	width  : number;
};

const staveExtentionInitializer : StaveExtention = {
	offset : 0,
	width  : 0
};

export interface Beat {
	notes : Note[];
	width : number;
};

export const beatInitializer : Beat = {
	...staveExtentionInitializer,
	notes : []
};

export interface Bar extends StaveExtention {
	measure : Measure;
};

export const barInitializer : Bar = {
	...staveExtentionInitializer,
	measure : measureInitializer
};

export interface Stave extends StaveExtention {
	bars : Bar[];
};

export const staveInitializer : Stave = {
	...staveExtentionInitializer,
	bars : []
};
