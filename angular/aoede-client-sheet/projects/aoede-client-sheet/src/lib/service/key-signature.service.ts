import { Injectable } from '@angular/core';

import { SingleCanvasService } from './canvas.service';
import { SheetConfiguration } from '../model/sheet-configuration.model';
import { StaveConfiguration } from '../model/stave-configuration.model';

import { KeySignature } from '../model/key-signature.model';
import { MappedKeySignature, staveExtentionInitializer } from '../model/stave.model';

@Injectable({
	providedIn: 'root'
})
export class KeySignatureService implements SingleCanvasService<KeySignature, MappedKeySignature> {

	constructor() { }

	public map  (source : KeySignature, staveConfig : StaveConfiguration, sheetConfig : SheetConfiguration): MappedKeySignature {
		return {
			...staveExtentionInitializer,
			keySignature : source,
			header : staveConfig.stavesHalfHeight,
			footer : staveConfig.stavesHalfHeight,
			width  : staveConfig.stavesFullHeight
		}
	}

	public draw (key : MappedKeySignature, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number = 0, y : number = 0) : void {
		context.save();
		context.fillRect(x, y - key.header, key.width, key.header);
		context.fillRect(x, y             , key.width, key.footer);
		context.font         = "bold " + ((key.header + key.footer) * 1.2) + "px ''";
		context.textAlign    = "center";
		context.textBaseline = "alphabetic";
		context.fillStyle    = "white";
		context.fillText(""+key.keySignature.id, x + (key.width / 2), y + key.footer);
		context.restore();
	}

}
