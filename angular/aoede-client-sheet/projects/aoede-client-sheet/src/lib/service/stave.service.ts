import { Injectable } from '@angular/core';

import { ArrayCanvasService } from './canvas.service';
import { BarService } from './bar.service';
import { ClefService } from './clef.service';
import { SheetConfiguration } from '../model/sheet-configuration.model';
import { StaveConfiguration } from '../model/stave-configuration.model';

import { Track } from '../model/track.model';
import {
	MappedStave,
	mappedStaveInitializer,
	MappedBar,
	mappedBarInitializer
} from '../model/stave.model';

@Injectable({
	providedIn: 'root'
})
export class StaveService implements ArrayCanvasService<Track, MappedStave> {

	constructor(
		private barService  : BarService,
		private clefService : ClefService
	) { }

	public map  (source : Track[], staveConfig : StaveConfiguration, sheetConfig : SheetConfiguration): MappedStave[] {
		let staves = this.barService
			.map (source, staveConfig, sheetConfig)
			.reduce ((staves : MappedStave[], bar : MappedBar) : MappedStave[] => {
				let bottom = staves[staves.length - 1];

				if (bottom.width + bar.width > staveConfig.stavesWidth) {
					bottom = this.emptyStave(source, staveConfig, sheetConfig);
					staves.push(bottom);
				}

				bottom.bars.push (bar);
				bottom.width += bar.width;

				return staves;
			}, [this.emptyStave(source, staveConfig, sheetConfig)] as MappedStave[]);

		staves.forEach ((stave) => {
			let adjusted = this.barService.normalize(stave.bars);
			stave.width  = adjusted.width + stave.offset;
			stave.header = adjusted.header;
			stave.footer = adjusted.footer;
			stave.tracks = adjusted.tracks;
		});

		return staves;
	}

	private emptyStave (source : Track[], staveConfig : StaveConfiguration, sheetConfig : SheetConfiguration) : MappedStave {
		let clefs  = sheetConfig.showTracks.map(
			track => this.clefService.map(sheetConfig.clefArray[source[track].clef], staveConfig, sheetConfig)
		);

		let offset = staveConfig.stavesSpacing * 2
			+ clefs.reduce((total, clef) => clef.width > total ? clef.width : total, 0);

		return {
			...mappedStaveInitializer(),
			header : 0,
			offset : offset,
			width  : offset,
			footer : staveConfig.noteSpacing * 12 + staveConfig.lineHeight * 6,
			clefs  : clefs
		};
	}

	public draw (stave : MappedStave, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number, y : number) : void {
		x += staveConfig.stavesMargin;

		stave.tracks.forEach((track, i) => {
			this.setupStave(track, staveConfig, context, x, y);
			this.clefService.draw(stave.clefs[i], staveConfig, context, x + staveConfig.stavesSpacing, y + track);
		});

		this.finishStave(stave, staveConfig, context, x, y);

		this.barService.draw (stave.bars, staveConfig, context, x + stave.offset, y);
	}

	private setupStave (track : number, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number, y : number) : void {
		[-4, -2, 0, 2, 4].forEach(i => {
			let yline = staveConfig.noteSpacing * i + track + y;
			context.fillRect(
				x,
				yline,
				staveConfig.stavesWidth,
				staveConfig.lineHeight
			);
		});
	}

	private finishStave (stave : MappedStave, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number, y : number) : void {
		let len = stave.tracks.length - 1;
		let top = stave.tracks[ 0 ] - 4 * staveConfig.noteSpacing;
		let end = stave.tracks[len] + 4 * staveConfig.noteSpacing;

		context.fillRect(x + staveConfig.stavesWidth - staveConfig.lineHeight, y + top, staveConfig.lineHeight, end - top);
	}

}
