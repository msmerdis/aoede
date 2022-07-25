import { Injectable } from '@angular/core';

import { SingleCanvasService } from './canvas.service';
import { BeatService } from './beat.service';
import { SheetConfiguration } from '../model/sheet-configuration.model';
import { StaveConfiguration } from '../model/stave-configuration.model';

import { Measure } from '../model/measure.model';
import { Clef, clefInitializer } from '../model/clef.model';
import { MappedMeasure, mappedMeasureInitializer } from '../model/stave.model';

@Injectable({
	providedIn: 'root'
})
export class MeasureService implements SingleCanvasService<Measure, MappedMeasure> {

	constructor(
		private beatService : BeatService
	) { }

	public map  (source : Measure, staveConfig : StaveConfiguration, sheetConfig : SheetConfiguration, beats : number[] = [0], clef : Clef = clefInitializer()): MappedMeasure {
		let mappedMeasure = mappedMeasureInitializer();

		// set minimum sizes
		mappedMeasure.header = staveConfig.noteSpacing * 12 + staveConfig.lineHeight * 6;
		mappedMeasure.footer = staveConfig.noteSpacing * 12 + staveConfig.lineHeight * 6;

		// append beats
		mappedMeasure.separator = staveConfig.stavesLineHeight;
		mappedMeasure.beats     = this.beatService.map (source, staveConfig, sheetConfig, beats, clef);
		mappedMeasure.beats.forEach (beat => {
			mappedMeasure.width += beat.width + mappedMeasure.separator;
			mappedMeasure.header = Math.max(mappedMeasure.header, beat.header);
			mappedMeasure.footer = Math.max(mappedMeasure.footer, beat.footer);
		});

		if (mappedMeasure.width > 0) {
			mappedMeasure.width += mappedMeasure.separator;
		}

		return mappedMeasure;
	}

	public normalize (measure : MappedMeasure, width : number) : void {
		measure.separator += Math.floor((width - measure.width) / (measure.beats.length + 1));
		measure.width      = width;
	}

	public draw (target : MappedMeasure, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number, y : number) : void {
		target.beats.forEach(beat => {
			x += target.separator;
			this.beatService.draw(beat, staveConfig, context, x, y);
			x += beat.width;
		});
	}

}
