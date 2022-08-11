import { Injectable } from '@angular/core';

import { SingleCanvasService } from './canvas.service';
import { MeasureService } from './measure.service';
import { SheetConfiguration } from '../model/sheet-configuration.model';
import { StaveConfiguration } from '../model/stave-configuration.model';

import { Track } from '../model/track.model';
import { StaveMapState, staveMapStateInitializer } from '../model/stave-map-state.model';
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

	public map (source : Track[], staveConfig : StaveConfiguration, sheetConfig : SheetConfiguration, staveStates : StaveMapState[] = []) : MappedBar[] {
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
						staveStates[j] || {beats:[0]}
					);

					mappedBar.measures.push(mappedMeasure);
				});

			this.resize(mappedBar);
			mappedBars.push (mappedBar);
		}

		return mappedBars;
	}

	private calcMeasures (source : Track[]) : number {
		return source
			.map ((track) => track.measures.length)
			.reduce ((max, cur) => max > cur ? max : cur, 0);
	}

	public updateSignatures (bar : MappedBar, staveConfig : StaveConfiguration, showTime : boolean) : void {
		bar.measures.forEach(measure => {
			this.measureService.updateSignature(measure, staveConfig, showTime);
		});

		this.resize(bar);
	}

	private resize (bar : MappedBar) : void {
		bar.footer = 0;
		bar.measures.forEach(measure => {
			// one additional staveConfig.lineHeight to account for the end of the bar
			bar.width  = Math.max(bar.width, measure.width + bar.separator);
			bar.footer = bar.footer + measure.header + measure.footer;
		});
	}

	public normalize (bars : MappedBar[], excess : number) : MappedBarAdjustment {
		let ext = {...staveExtentionInitializer, tracks : []} as MappedBarAdjustment;
		let num = bars.length;
		let len = Math.floor((excess + num - 1) / num);

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

	public draw (target : MappedBar[], staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number, y : number, tracks : number[] = []) : void {
		target.forEach ((bar : MappedBar) => {
			this.drawBar (bar, staveConfig, context, x, y, tracks);
			x += bar.width;
		});
	}

	public drawBar (target : MappedBar, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number, y : number, tracks : number[]) : void {
		let end    = tracks.length - 1;
		let header = tracks[ 0 ] - staveConfig.stavesHalfHeight;
		let footer = tracks[end] + staveConfig.stavesHalfHeight;

		target.measures.forEach ((measure : MappedMeasure, i : number) => {
			this.measureService.draw(measure, staveConfig, context, x, y + tracks[i]);
		});

		context.fillRect(x + target.width - target.separator, y + header, target.separator, footer - header);
	}

}
