import { Injectable } from '@angular/core';

import { ArrayCanvasService } from './canvas.service';
import { BarService } from './bar.service';
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
		private barService : BarService
	) { }

	public map  (source : Track[], staveConfig : StaveConfiguration, sheetConfig : SheetConfiguration): MappedStave[] {
		return this.barService
			.map (source, staveConfig, sheetConfig)
			.reduce ((staves : MappedStave[], bar : MappedBar) : MappedStave[] => {
				var bottom = staves[staves.length - 1];

				if (bottom.width + bar.width > staveConfig.stavesWidth) {
					bottom = this.emptyStave(staveConfig);
					staves.push(bottom);
				}

				bottom.bars.push (bar);
				var extention = this.barService.normalize(bottom.bars);
				bottom.width  = extention.width;
				bottom.header = extention.header;
				bottom.footer = extention.footer;

				return staves;
			}, [this.emptyStave(staveConfig)] as MappedStave[]);
	}

	private emptyStave (staveConfig : StaveConfiguration) : MappedStave {
		return {
			...mappedStaveInitializer(),
			header : 0,
			footer : staveConfig.noteSpacing * 12 + staveConfig.lineHeight * 6
		};
	}

	public draw (stave : MappedStave, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number, y : number) : void {
		this.barService.draw(stave.bars, staveConfig, context, x + staveConfig.stavesMargin, y);
	}

}
