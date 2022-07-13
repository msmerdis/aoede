import { Clef } from './clef.model';
import { KeySignature } from './key-signature.model';

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
