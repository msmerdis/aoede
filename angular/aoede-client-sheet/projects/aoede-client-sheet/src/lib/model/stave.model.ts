import { Sheet, sheetInitializer } from './sheet.model';
import { Measure, measureInitializer } from './measure.model';
import { Note, noteInitializer } from './note.model';
import { Clef, clefInitializer } from './clef.model';
import { TimeSignature, timeSignatureInitializer } from './time-signature.model';
import { KeySignature, keySignatureInitializer } from './key-signature.model';

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
	notes     : MappedNote[],
	separator : number
};

export function mappedBeatInitializer () : MappedBeat {
	return {
		...staveExtentionInitializer,
		notes     : [],
		separator : 0
	};
};

export interface MappedMeasure extends StaveExtention {
	measure   : Measure;
	beats     : MappedBeat[];
	separator : number;
};

export function mappedMeasureInitializer () : MappedMeasure {
	return {
		...staveExtentionInitializer,
		measure   : measureInitializer(),
		beats     : [],
		separator : 0
	};
};

export interface MappedBar extends StaveExtention {
	measures  : MappedMeasure[];
	separator : number;
};

export function mappedBarInitializer (separator : number = 0) : MappedBar {
	return {
		...staveExtentionInitializer,
		measures  : [],
		separator : separator
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

export interface MappedKeySignature extends StaveExtention {
	keySignature : KeySignature;
};

export function mappedKeySignatureInitializer () : MappedKeySignature {
	return {
		...staveExtentionInitializer,
		keySignature : keySignatureInitializer()
	};
};

export interface MappedTimeSignature extends StaveExtention {
	time : TimeSignature;
};

export function mappedTimeSignatureInitializer () : MappedTimeSignature {
	return {
		...staveExtentionInitializer,
		time : timeSignatureInitializer()
	};
};

export interface MappedStaveSignature extends StaveExtention {
	clef       : MappedClef;
	clefOffset : number;
	time       : MappedTimeSignature;
	timeOffset : number;
	key        : MappedKeySignature;
	keyOffset  : number;
};

export function mappedStaveSignatureInitializer () : MappedStaveSignature {
	return {
		...staveExtentionInitializer,
	clef       : mappedClefInitializer(),
	clefOffset : 0,
	time       : mappedTimeSignatureInitializer(),
	timeOffset : 0,
	key        : mappedKeySignatureInitializer(),
	keyOffset  : 0
	};
};

export interface MappedStave extends StaveExtention {
	bars       : MappedBar            [];
	tracks     : number               [];
	signatures : MappedStaveSignature [];
	offset     : number;
};

export function mappedStaveInitializer () : MappedStave {
	return {
		...staveExtentionInitializer,
		bars       : [],
		tracks     : [],
		signatures : [],
		offset     : 0
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