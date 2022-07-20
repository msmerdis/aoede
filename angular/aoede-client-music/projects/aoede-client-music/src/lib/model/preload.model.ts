import {
	Clef,
	KeySignature,
	Tempo
} from 'aoede-client-sheet';

export interface Preload {
	clefList  : Clef[];
	keysList  : KeySignature[];
	tempoList : any[];
};

export function preloadInitializer () : Preload {
	return {
		clefList  : [],
		keysList  : [],
		tempoList : []
	};
};
