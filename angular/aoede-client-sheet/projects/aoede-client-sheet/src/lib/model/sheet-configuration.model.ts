import { Clef  } from '../model/clef.model';
import { KeySignature } from '../model/key-signature.model';
import { TimeSignature } from '../model/time-signature.model';

export interface SheetConfiguration {
	clefArray  : {[index : string] : Clef};
	keysArray  : {[index : number] : KeySignature};
	timesList  : TimeSignature[];
	normalize  : boolean;
	showHeader : boolean;
	showFooter : boolean;
	showTracks : number[];
};

export function sheetConfigurationInitializer () : SheetConfiguration {
	return {
		clefArray  : {},
		keysArray  : {},
		timesList  : [],
		normalize  : true,
		showHeader : false,
		showFooter : false,
		showTracks : [0]
	};
};