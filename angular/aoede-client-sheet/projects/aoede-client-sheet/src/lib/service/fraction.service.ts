import { Injectable } from '@angular/core';

import { SingleCanvasService } from './canvas.service';
import { SheetConfiguration } from '../model/sheet-configuration.model';
import { StaveConfiguration } from '../model/stave-configuration.model';

import { Fraction } from '../model/fraction.model';
import { MappedFraction, staveExtentionInitializer } from '../model/stave.model';

@Injectable({
	providedIn: 'root'
})
export class FractionService implements SingleCanvasService<Fraction, MappedFraction> {

	constructor() { }

	public map  (source : Fraction, staveConfig : StaveConfiguration, sheetConfig : SheetConfiguration): MappedFraction {
		let length = Math.max(
			source.numerator.toString().length,
			source.denominator.toString().length
		);

		return {
			...staveExtentionInitializer,
			fraction : source,
			width    : staveConfig.stavesHalfHeight * length,
			header   : staveConfig.stavesHalfHeight,
			footer   : staveConfig.stavesHalfHeight
		}
	}

	public draw (fraction : MappedFraction, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number = 0, y : number = 0) : void {
		let midPoint = x + (fraction.width+1)/2;

		context.save();
		context.font         = "bold " + (fraction.header * 1.2) + "px ''";
		context.textAlign    = "center";
		context.textBaseline = "alphabetic";
		context.save();
		context.translate(midPoint, y);
		context.fillText(fraction.fraction.numerator.toString (), 0, 0);
		context.restore();
		context.save();
		context.translate(midPoint, y + staveConfig.stavesHalfHeight);
		context.fillText(fraction.fraction.denominator.toString (), 0, 0);
		context.restore();
		context.restore();
	}

}