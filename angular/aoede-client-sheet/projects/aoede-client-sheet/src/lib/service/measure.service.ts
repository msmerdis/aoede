import { Injectable } from '@angular/core';

import { SingleCanvasService } from './canvas.service';
import { BeatService } from './beat.service';
import { StaveSignatureService } from './stave-signature.service';
import { SheetConfiguration } from '../model/sheet-configuration.model';
import { StaveConfiguration } from '../model/stave-configuration.model';

import { Measure } from '../model/measure.model';
import { KeySignature } from '../model/key-signature.model';
import { StaveMapState, staveMapStateInitializer } from '../model/stave-map-state.model';
import { MappedMeasure, mappedMeasureInitializer } from '../model/stave.model';

@Injectable({
	providedIn: 'root'
})
export class MeasureService implements SingleCanvasService<Measure, MappedMeasure> {

	constructor(
		private beatService : BeatService,
		private staveSignatureService : StaveSignatureService
	) { }

	public map  (source : Measure, staveConfig : StaveConfiguration, sheetConfig : SheetConfiguration, staveState : StaveMapState = staveMapStateInitializer()): MappedMeasure {
		let mappedMeasure = mappedMeasureInitializer();

		mappedMeasure.measure = source;

		// set minimum sizes
		mappedMeasure.header = staveConfig.noteSpacing * 12 + staveConfig.lineHeight * 6;
		mappedMeasure.footer = staveConfig.noteSpacing * 12 + staveConfig.lineHeight * 6;

		// map the measure signature
		mappedMeasure.signature = this.staveSignatureService.map(source, staveConfig, sheetConfig, staveState);
		mappedMeasure.width     = mappedMeasure.signature.width;

		if (mappedMeasure.width > 0) {
			mappedMeasure.width += staveConfig.stavesSpacing;
		}

		// append beats

		mappedMeasure.separator = staveConfig.stavesSpacing;
		mappedMeasure.beats     = this.beatService.map (source, staveConfig, sheetConfig, staveState.beats);
		mappedMeasure.beats.forEach (beat => {
			mappedMeasure.width += beat.width + mappedMeasure.separator;
			mappedMeasure.count += 1;
			mappedMeasure.header = Math.max(mappedMeasure.header, beat.header);
			mappedMeasure.footer = Math.max(mappedMeasure.footer, beat.footer);
		});

		if (mappedMeasure.width > 0) {
			mappedMeasure.width += mappedMeasure.separator;
			mappedMeasure.count += 1;
		}

		return mappedMeasure;
	}

	public updateSignature (measure : MappedMeasure, staveConfig : StaveConfiguration, showTime : boolean) : void {
		if (measure.signature.width > 0) {
			measure.width -= measure.signature.width;
			measure.width -= staveConfig.stavesSpacing;
		}

		this.staveSignatureService.updateSignature(measure.signature, true, showTime);

		if (measure.signature.width > 0) {
			measure.width += measure.signature.width;
			measure.width += staveConfig.stavesSpacing;
		}
	}

	public normalize (measure : MappedMeasure, width : number) : void {
		let excess = Math.floor((width - measure.width + 2 * measure.beats.length) / (2 * measure.beats.length + 1));

		measure.beats.forEach(beat => this.beatService.normalize(beat, excess));
		measure.separator += excess;
		measure.width      = width;
	}

	public draw (target : MappedMeasure, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number, y : number) : void {
		console.log("draw measure : " + JSON.stringify(target));
		if (target.signature.width > 0) {
			x += staveConfig.stavesSpacing;
			this.staveSignatureService.draw(target.signature, staveConfig, context, x, y);
			x += target.signature.width;
		}

		target.beats.forEach(beat => {
			x += target.separator;
			this.beatService.draw(beat, staveConfig, context, x, y);
			x += beat.width;
		});
	}

}
