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
			stave.width  = adjusted.width;
			stave.header = adjusted.header;
			stave.footer = adjusted.footer;
			stave.tracks = adjusted.tracks;
		});

		return staves;
	}

	private emptyStave (source : Track[], staveConfig : StaveConfiguration, sheetConfig : SheetConfiguration) : MappedStave {
		let clefs = sheetConfig.showTracks.map(track => this.clefService.map(sheetConfig.clefArray[source[track].clef], staveConfig, sheetConfig));

		return {
			...mappedStaveInitializer(),
			header : 0,
			width  : staveConfig.noteSpacing * 8 + clefs.reduce((total, clef) => clef.width > total ? clef.width : total, 0),
			footer : staveConfig.noteSpacing * 12 + staveConfig.lineHeight * 6,
			clefs  : clefs
		};
	}

	public draw (stave : MappedStave, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number, y : number) : void {
		x += staveConfig.stavesMargin;

		this.barService.draw (stave.bars, staveConfig, context, x, y);

		stave.clefs.forEach((clef, i) =>
			this.clefService.draw(clef, staveConfig, context, x + staveConfig.noteSpacing * 4, y + stave.tracks[i])
		);
	}

}
