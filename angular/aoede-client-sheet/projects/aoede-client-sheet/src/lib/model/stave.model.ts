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

export interface MappedClef extends StaveExtention {
	clef   : Clef;
	note   : number;
	octave : number;
};

export function mappedClefInitializer () : MappedClef {
	return {
		...staveExtentionInitializer,
		clef   : clefInitializer(),
		note   : 0,
		octave : 0
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
	time      : MappedTimeSignature;
	key       : MappedKeySignature;
	separator : number;
	showTime  : boolean;
	showKey   : boolean;
};

export function mappedStaveSignatureInitializer () : MappedStaveSignature {
	return {
		...staveExtentionInitializer,
		time      : mappedTimeSignatureInitializer(),
		key       : mappedKeySignatureInitializer(),
		separator : 0,
		showTime  : false,
		showKey   : false
	};
};

export interface MappedNote extends StaveExtention {
	note       : Note;
	accidental : number;
};

export function mappedNoteInitializer () : MappedNote {
	return {
		...staveExtentionInitializer,
		note       : noteInitializer(),
		accidental : 0
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
	count     : number;
	separator : number;
	signature : MappedStaveSignature;
};

export function mappedMeasureInitializer () : MappedMeasure {
	return {
		...staveExtentionInitializer,
		measure   : measureInitializer(),
		beats     : [],
		count     : 0,
		separator : 0,
		signature : mappedStaveSignatureInitializer()
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

export interface MappedStave extends StaveExtention {
	clefs      : MappedClef           [];
	bars       : MappedBar            [];
	tracks     : number               [];
	signatures : MappedStaveSignature [];
	offset     : number;
};

export function mappedStaveInitializer () : MappedStave {
	return {
		...staveExtentionInitializer,
		clefs      : [],
		bars       : [],
		tracks     : [],
		signatures : [],
		offset     : 0,
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