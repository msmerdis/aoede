import { Injectable } from '@angular/core';

import { SingleCanvasService } from './canvas.service';
import { BeatService } from './beat.service';
import { SheetConfiguration } from '../model/sheet-configuration.model';
import { StaveConfiguration } from '../model/stave-configuration.model';

import { Measure } from '../model/measure.model';
import { MappedMeasure, mappedMeasureInitializer } from '../model/stave.model';

@Injectable({
	providedIn: 'root'
})
export class MeasureService implements SingleCanvasService<Measure, MappedMeasure> {

	constructor(
		private beatService : BeatService
	) { }

	public map  (source : Measure, staveConfig : StaveConfiguration, sheetConfig : SheetConfiguration, beats : number[] = [0], pitch : number = 0): MappedMeasure {
		var mappedMeasure = mappedMeasureInitializer();

		mappedMeasure.width  = staveConfig.scale * 250;
		mappedMeasure.header = staveConfig.noteSpacing * 12 + staveConfig.lineHeight * 6;
		mappedMeasure.footer = staveConfig.noteSpacing * 12 + staveConfig.lineHeight * 6;

		return mappedMeasure;
	}

	public normalize (measure : MappedMeasure, width : number) : void {
		measure.width = width;
	}

	public draw (target : MappedMeasure, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D) : void {
	}

}