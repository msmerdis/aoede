import { Injectable } from '@angular/core';

import { SingleCanvasService } from './canvas.service';
import { SheetConfiguration } from '../model/sheet-configuration.model';
import { StaveConfiguration } from '../model/stave-configuration.model';

import { TimeSignature } from '../model/time-signature.model';
import { MappedTimeSignature, staveExtentionInitializer } from '../model/stave.model';

@Injectable({
	providedIn: 'root'
})
export class TimeSignatureService implements SingleCanvasService<TimeSignature, MappedTimeSignature> {

	constructor() { }

	public map  (source : TimeSignature, staveConfig : StaveConfiguration, sheetConfig : SheetConfiguration): MappedTimeSignature {
		let length = Math.max(
			source.numerator.toString().length,
			source.denominator.toString().length
		);

		return {
			...staveExtentionInitializer,
			time   : source,
			width  : staveConfig.stavesHalfHeight * length,
			header : staveConfig.stavesHalfHeight,
			footer : staveConfig.stavesHalfHeight
		}
	}

	public draw (time : MappedTimeSignature, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number = 0, y : number = 0) : void {
		let midPoint = x + (time.width+1)/2;

		context.save();
		context.font         = "bold " + (time.header * 1.2) + "px ''";
		context.textAlign    = "center";
		context.textBaseline = "alphabetic";
		context.save();
		context.translate(midPoint, y);
		context.fillText(time.time.numerator.toString (), 0, 0);
		context.restore();
		context.save();
		context.translate(midPoint, y + staveConfig.stavesHalfHeight);
		context.fillText(time.time.denominator.toString (), 0, 0);
		context.restore();
		context.restore();
	}

}