import { Injectable } from '@angular/core';

import { SingleCanvasService } from './canvas.service';
import { MeasureService } from './measure.service';
import { SheetConfiguration } from '../model/sheet-configuration.model';
import { StaveConfiguration } from '../model/stave-configuration.model';

import { Track } from '../model/track.model';
import { Clef, clefInitializer } from '../model/clef.model';
import {
	StaveExtention,
	staveExtentionInitializer,
	MappedStave,
	MappedBar,
	MappedBarAdjustment,
	mappedBarInitializer,
	MappedMeasure
} from '../model/stave.model';

@Injectable({
	providedIn: 'root'
})
export class BarService implements SingleCanvasService<Track[], MappedBar[]> {

	constructor(
		private measureService : MeasureService
	) { }

	public map (source : Track[], staveConfig : StaveConfiguration, sheetConfig : SheetConfiguration, beats : number[][] = [], clefs : Clef[] = []) : MappedBar[] {
		let measures   = this.calcMeasures (source);
		let mappedBars = [] as MappedBar[];

		for (let i = 0; i < measures; i += 1) {
			let mappedBar = mappedBarInitializer(staveConfig.lineHeight);

			sheetConfig.showTracks
				.filter(n => n < source.length)
				.forEach(j => {
					let mappedMeasure = this.measureService.map (
						source[j].measures[i],
						staveConfig,
						sheetConfig,
						beats[j] || [0],
						clefs[j] || clefInitializer()
					);

					// one additional staveConfig.lineHeight to account for the end of the bar
					mappedBar.width  = Math.max(mappedBar.width, mappedMeasure.width + mappedBar.separator);
					mappedBar.footer = mappedBar.footer + mappedMeasure.header + mappedMeasure.footer;
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

	public normalize (bars : MappedBar[], excess : number) : MappedBarAdjustment {
		let ext = {...staveExtentionInitializer, tracks : []} as MappedBarAdjustment;
		let num = bars.length;
		let len = Math.floor(excess / num);

		ext = bars.reduce ((e : MappedBarAdjustment, bar : MappedBar, i : number) => {
			// last bar will contain the round up
			if (i + 1 == num) {
				len += (excess - len * num);
			}

			bar.width += len;
			bar.measures.forEach(
				m => this.measureService.normalize(m, bar.width - bar.separator)
			);

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
	}

	public drawBar (target : MappedBar, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number, y : number) : void {
		let end    = target.measures.length - 1;
		let header = target.measures[ 0 ].header - staveConfig.stavesHalfHeight;
		let footer = target.measures[end].footer - staveConfig.stavesHalfHeight;
		let offset = y;

		target.measures.forEach ((measure : MappedMeasure) => {
			offset += measure.header;
			this.measureService.draw(measure, staveConfig, context, x, offset);
			offset += measure.footer;
		});

		context.fillRect(x + target.width - target.separator, y + header, target.separator, target.footer - header - footer);
	}

}
