import {
	Clef,
	KeySignature,
	Tempo,
	TimeSignature
} from 'aoede-client-sheet';

export interface Preload {
	clefList  : Clef[];
	keysList  : KeySignature[];
	tempoList : any[];
	timeList  : TimeSignature[];
};

export function preloadInitializer () : Preload {
	return {
		clefList  : [],
		keysList  : [],
		tempoList : [],
		timeList  : []
	};
};
