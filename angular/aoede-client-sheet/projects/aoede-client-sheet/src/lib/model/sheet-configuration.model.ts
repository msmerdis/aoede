import { Clef  } from '../model/clef.model';
import { KeySignature } from '../model/key-signature.model';

export interface SheetConfiguration {
	clefArray  : {[index : string] : Clef};
	keysArray  : {[index : number] : KeySignature};
	showHeader : boolean;
	showFooter : boolean;
	firstTrack : number;
};

export const sheetConfigurationInitializer : SheetConfiguration = {
	clefArray  : {},
	keysArray  : {},
	showHeader : false,
	showFooter : false,
	firstTrack : 0
};