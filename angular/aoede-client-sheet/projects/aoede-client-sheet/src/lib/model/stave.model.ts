import { Sheet, sheetInitializer } from './sheet.model';
import { Measure, measureInitializer } from './measure.model';
import { Note, noteInitializer } from './note.model';

export interface StaveExtention {
	width  : number;
	header : number;
	footer : number;
};

export const staveExtentionInitializer : StaveExtention = {
	width  : 0,
	header : 0,
	footer : 0
};

/////////////////////////////////////////////////
// Old
/////////////////////////////////////////////////

export interface Stack extends StaveExtention {
	notes : Note[];
};

export const stackInitializer : Stack = {
	...staveExtentionInitializer,
	notes : []
};

export interface Beat extends StaveExtention {
	notes : Stack[];
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
	measure : measureInitializer()
};

export interface Stave extends StaveExtention {
	bars : Bar[];
};

export const staveInitializer : Stave = {
	...staveExtentionInitializer,
	bars : []
};

/////////////////////////////////////////////////
// New
/////////////////////////////////////////////////

export interface MappedNote extends StaveExtention {
	note : Note;
};

export function mappedNoteInitializer () : MappedNote {
	return {
		...staveExtentionInitializer,
		note : noteInitializer()
	};
};

export interface MappedBeat extends StaveExtention {
	notes : MappedNote[]
};

export function mappedBeatInitializer () : MappedBeat {
	return {
		...staveExtentionInitializer,
		notes : []
	};
};

export interface MappedMeasure extends StaveExtention {
	measure : Measure;
	beats   : MappedBeat[];
	offset  : number;
};

export function mappedMeasureInitializer () : MappedMeasure {
	return {
		...staveExtentionInitializer,
		measure : measureInitializer(),
		beats   : [],
		offset  : 0
	};
};

export interface MappedBar extends StaveExtention {
	measures : MappedMeasure[];
};

export function mappedBarInitializer () : MappedBar {
	return {
		...staveExtentionInitializer,
		measures : []
	};
};

export interface MappedStave extends StaveExtention {
	bars   : MappedBar [];
};

export function mappedStaveInitializer () : MappedStave {
	return {
		...staveExtentionInitializer,
		bars   : []
	};
};

export interface MappedSheet extends StaveExtention {
	sheet  : Sheet;
	staves : MappedStave[];
	mapped : boolean
};

export function mappedSheetInitializer () : MappedSheet {
	return {
		...staveExtentionInitializer,
		sheet  : sheetInitializer(),
		staves : [],
		mapped : false
	};
};