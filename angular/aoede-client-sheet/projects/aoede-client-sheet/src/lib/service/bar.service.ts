import { Injectable } from '@angular/core';

import { SingleCanvasService } from './canvas.service';
import { MeasureService } from './measure.service';
import { SheetConfiguration } from '../model/sheet-configuration.model';
import { StaveConfiguration } from '../model/stave-configuration.model';

import { Track } from '../model/track.model';
import {
	StaveExtention,
	staveExtentionInitializer,
	MappedStave,
	MappedBar,
	MappedBarAdjustment,
	mappedBarInitializer
} from '../model/stave.model';

@Injectable({
	providedIn: 'root'
})
export class BarService implements SingleCanvasService<Track[], MappedBar[]> {

	constructor(
		private measureService : MeasureService
	) { }

	public map (source : Track[], staveConfig : StaveConfiguration, sheetConfig : SheetConfiguration) : MappedBar[] {
		let measures   = this.calcMeasures (source);
		let mappedBars = [] as MappedBar[];

		for (let i = 0; i < measures; i += 1) {
			let mappedBar = mappedBarInitializer();

			sheetConfig.showTracks
				.filter(n => n < source.length)
				.forEach(j => {
					let mappedMeasure = this.measureService.map (
						source[j].measures[i],
						staveConfig,
						sheetConfig
					);

					// adjust offset to the top of the bar
					mappedMeasure.offset = mappedMeasure.header + mappedBar.footer;

					mappedBar.width  = Math.max(mappedBar.width, mappedMeasure.width);
					mappedBar.footer = mappedMeasure.offset + mappedMeasure.footer;
					mappedBar.measures.push(mappedMeasure);
				});

			mappedBars.push (mappedBar);
		}

		return mappedBars;
	}

	private calcMeasures (source : Track[]) : number {
		return source
			.map ((track) => track.measures.length)
			.reduce ((max, cur) => max > cur ? max : cur, 0);
	}

	public normalize (bars : MappedBar[]) : MappedBarAdjustment {
		let ext = {...staveExtentionInitializer, tracks : []} as MappedBarAdjustment;

		ext = bars.reduce ((e : MappedBarAdjustment, bar : MappedBar) => {
			return {
				...e,
				header : Math.max(e.header, bar.header),
				footer : Math.max(e.footer, bar.footer),
				width  : e.width + bar.width,
				tracks : this.updateTrackCenters(e.tracks, bar)
			}
		}, ext);

		return ext;
	}

	private updateTrackCenters (cur : number[], bar : MappedBar) : number[] {
		let offset : number = 0;
		return bar.measures.map((measure, i) => {
			let rtn = Math.max(measure.header + offset, cur[i] || 0);
			offset  = measure.footer + rtn;

			return rtn;
		});
	}

	public draw (target : MappedBar[], staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number, y : number) : void {
		target.forEach ((bar : MappedBar) => {
			this.drawBar (bar, staveConfig, context, x, y);
			x += bar.width;
		});
		// TODO remove
		// end of all bars ruler
		context.fillRect(x, y, staveConfig.lineHeight, target[0].footer);
	}

	public drawBar (target : MappedBar, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number, y : number) : void {
		// TODO remove
		// start of bar ruler
		context.fillRect(x, y, staveConfig.lineHeight, target.footer);
	}

}
