import { Sheet, sheetInitializer } from './sheet.model';
import { Measure, measureInitializer } from './measure.model';
import { Note, noteInitializer } from './note.model';
import { Clef, clefInitializer } from './clef.model';

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

export interface MappedBarAdjustment extends StaveExtention {
	tracks : number[];
};

export interface MappedClef extends StaveExtention {
	clef : Clef;
};

export function mappedClefInitializer () : MappedClef {
	return {
		...staveExtentionInitializer,
		clef : clefInitializer()
	};
};

export interface MappedStave extends StaveExtention {
	bars   : MappedBar  [];
	tracks : number     [];
	clefs  : MappedClef [];
	offset : number;
};

export function mappedStaveInitializer () : MappedStave {
	return {
		...staveExtentionInitializer,
		bars   : [],
		tracks : [],
		clefs  : [],
		offset : 0
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