import { Clef  } from '../model/clef.model';
import { KeySignature } from '../model/key-signature.model';

export interface StaveConfiguration {
	stavesMargin  : number;
	stavesPadding : number;
	stavesWidth   : number;
	/*
	stavesHeight  : number;
	headerHeight  : number;
	footerHeight  : number;
	*/
	lineHeight    : number;
	noteSpacing   : number;
};

export function staveConfigurationInitializer () : StaveConfiguration {
	return {
		stavesMargin  :  11 * 5,
		stavesPadding :  11 * 5,
		stavesWidth   : 188 * 5,
		/*
		stavesHeight  :  33 * 5,
		headerHeight  :  22 * 5,
		footerHeight  :  11 * 5,
		*/
		lineHeight    :       1,
		noteSpacing   :       5
	};
};