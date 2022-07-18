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

export const preloadInitializer : Preload = {
	clefList  : [],
	keysList  : [],
	tempoList : []
};
