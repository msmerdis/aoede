export interface NoteOffset {
	offset     : number;
	accidental : number;
};

export interface KeySignature {
	id     : number;
	major  : string;
	minor  : string;
	notes? : NoteOffset[];
};

export function keySignatureInitializer () : KeySignature {
	return {
		id    : 0,
		major : "C",
		minor : "a"
	};
};

export interface KeySignatureArray {
	[key : number] : KeySignature;
};
