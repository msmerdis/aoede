import { Clef  } from '../model/clef.model';
import { KeySignature } from '../model/key-signature.model';

export interface SheetConfiguration {
	clefArray  : {[index : string] : Clef};
	keysArray  : {[index : number] : KeySignature};
	showHeader : boolean;
	showFooter : boolean;
	showTracks : number[];
};

export function sheetConfigurationInitializer () : SheetConfiguration {
	return {
		clefArray  : {},
		keysArray  : {},
		showHeader : false,
		showFooter : false,
		showTracks : [0]
	};
};