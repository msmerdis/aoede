import { Injectable } from '@angular/core';

import { ArrayCanvasService } from './canvas.service';
import { BarService } from './bar.service';
import { ClefService } from './clef.service';
import { StaveSignatureService } from './stave-signature.service';
import { SheetConfiguration } from '../model/sheet-configuration.model';
import { StaveConfiguration } from '../model/stave-configuration.model';

import { StaveMapState } from '../model/stave-map-state.model';
import { Track } from '../model/track.model';
import {
	MappedStave,
	mappedStaveInitializer,
	MappedBar
} from '../model/stave.model';

@Injectable({
	providedIn: 'root'
})
export class StaveService implements ArrayCanvasService<Track, MappedStave> {

	constructor(
		private barService : BarService,
		private clefService : ClefService,
		private staveSignatureService : StaveSignatureService
	) { }

	public map (source : Track[], staveConfig : StaveConfiguration, sheetConfig : SheetConfiguration): MappedStave[] {
		let states : StaveMapState[] = source.map(track =>
			this.staveSignatureService.initialize(track, staveConfig, sheetConfig)
		);
		let maxBarWidth = 0;
		let bars = this.barService.map (source, staveConfig, sheetConfig, states);

		bars.forEach((b) => maxBarWidth = Math.max(maxBarWidth, b.width));

		let staves = bars.reduce ((staves : MappedStave[], bar : MappedBar) : MappedStave[] => {
			let bottom = staves[staves.length - 1];

			if (bottom.width + bar.width >= Math.max(staveConfig.stavesWidth, maxBarWidth + bottom.offset)) {
				bottom = this.emptyStave(states, staveConfig, sheetConfig);
				staves.push(bottom);
			}

			if (bottom.bars.length == 0) {
				this.barService.updateSignatures(bar, staveConfig, staves.length == 1);
				maxBarWidth = Math.max(maxBarWidth, bar.width);
			}

			bottom.bars.push (bar);
			bottom.width += bar.width;

			return staves;
		}, [this.emptyStave(states, staveConfig, sheetConfig)] as MappedStave[]);

		staves.forEach ((stave) => {
			let maximum  = Math.max(staveConfig.stavesWidth, maxBarWidth + stave.offset);
			let excess   = sheetConfig.normalize ? (maximum - stave.width) : 0;
			let adjusted = this.barService.normalize(stave.bars, excess);
			stave.width  = adjusted.width + stave.offset;
			stave.header = adjusted.header;
			stave.footer = adjusted.footer;
			stave.tracks = adjusted.tracks;
		});

		return staves;
	}

	private emptyStave (source : StaveMapState[], staveConfig : StaveConfiguration, sheetConfig : SheetConfiguration) : MappedStave {
		let clefs = sheetConfig.showTracks.map(track => source[track].mappedClef);
		let width = clefs.reduce((max, clef) => clef.width > max ? clef.width : max, 0);

		if (width > 0) {
			width += staveConfig.stavesSpacing;
		}

		return {
			...mappedStaveInitializer(),
			header     : 0,
			clefs      : clefs,
			offset     : width,
			width      : width,
			footer     : staveConfig.stavesLineHeight * 6
		};
	}

	public draw (stave : MappedStave, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number, y : number, scale : number = 1) : void {
		x += staveConfig.stavesMargin;

		stave.tracks.forEach((track, i) => {
			this.setupStave(staveConfig, context, x, y + track, scale);

			if (stave.offset > 0) {
				this.clefService.draw(stave.clefs[i], staveConfig, context, x + staveConfig.stavesSpacing, y + track);
			}
		});

		this.barService.draw (stave.bars, staveConfig, context, x + stave.offset, y, stave.tracks);
	}

	private setupStave (staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number, y : number, scale : number) : void {
		[-2, -1, 0, 1, 2].forEach(i => {
			let yline = staveConfig.stavesLineHeight * i + y;
			context.fillRect(
				x,
				yline,
				staveConfig.stavesWidth / scale,
				staveConfig.lineHeight
			);
		});
	}

}
