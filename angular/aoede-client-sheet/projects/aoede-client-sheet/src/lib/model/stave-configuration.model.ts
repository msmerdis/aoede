import { Clef  } from '../model/clef.model';
import { KeySignature } from '../model/key-signature.model';

export interface StaveConfiguration {
	scale         : number;
	stavesMargin  : number;
	stavesWidth   : number;
	/*
	stavesHeight  : number;
	headerHeight  : number;
	footerHeight  : number;
	*/
	lineHeight    : number;
	noteSpacing   : number;
};

export function staveConfigurationInitializer (scale : number = 3) : StaveConfiguration {
	return {
		scale         : scale,
		stavesMargin  :  11 * 5 * scale,
		stavesWidth   : 188 * 5 * scale,
		/*
		stavesHeight  :  33 * 5,
		headerHeight  :  22 * 5,
		footerHeight  :  11 * 5,
		*/
		lineHeight    :       1 * scale,
		noteSpacing   :       5 * scale
	};
};