import { Injectable } from '@angular/core';

import { SingleCanvasService } from './canvas.service';
import { NoteService } from './note.service';
import { SheetConfiguration } from '../model/sheet-configuration.model';
import { StaveConfiguration } from '../model/stave-configuration.model';

import { Measure } from '../model/measure.model';
import { MappedMeasure, mappedMeasureInitializer } from '../model/stave.model';

@Injectable({
	providedIn: 'root'
})
export class MeasureService implements SingleCanvasService<Measure, MappedMeasure> {

	constructor() { }

	public map  (source : Measure, staveConfig : StaveConfiguration, sheetConfig : SheetConfiguration): MappedMeasure {
		var mappedMeasure = mappedMeasureInitializer();

		mappedMeasure.width  = staveConfig.scale * 250;
		mappedMeasure.header = staveConfig.noteSpacing * 12 + staveConfig.lineHeight * 6;
		mappedMeasure.footer = staveConfig.noteSpacing * 12 + staveConfig.lineHeight * 6;

		return mappedMeasure;
	}

	public draw (target : MappedMeasure, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D) : void {
	}

}