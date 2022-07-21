import { Clef  } from '../model/clef.model';
import { KeySignature } from '../model/key-signature.model';

export interface StaveConfiguration {
	scale            : number;
	stavesMargin     : number;
	stavesWidth      : number;
	stavesSpacing    : number;
	stavesHalfHeight : number;
	stavesFullHeight : number;
	lineHeight       : number;
	noteSpacing      : number;
};

export function staveConfigurationInitializer (scale : number = 1) : StaveConfiguration {
	return {
		scale            : scale,
		stavesMargin     :  11 * 5 * scale,
		stavesWidth      : 188 * 5 * scale,
		stavesSpacing    :   2 * 5 * scale,
		stavesHalfHeight :   4 * 5 * scale,
		stavesFullHeight :   8 * 5 * scale,
		lineHeight       :       1 * scale,
		noteSpacing      :       5 * scale
	};
};