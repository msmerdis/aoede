import {
	TimeSignature,
	timeSignatureInitializer
} from 'aoede-client-sheet';

export interface GenerateSheet {
	name          : string;
	clef          : string;
	tempo         : number;
	keySignature  : number;
	timeSignature : TimeSignature;
};

export function generateSheetInitializer () : GenerateSheet {
	return {
		name          : "",
		clef          : "",
		tempo         : 0,
		keySignature  : 0,
		timeSignature : timeSignatureInitializer()
	};
};