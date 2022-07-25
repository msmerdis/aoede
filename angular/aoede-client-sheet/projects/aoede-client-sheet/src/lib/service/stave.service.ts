import { Injectable } from '@angular/core';

import { ArrayCanvasService } from './canvas.service';
import { BarService } from './bar.service';
import { ClefService } from './clef.service';
import { KeySignatureService } from './key-signature.service';
import { TimeSignatureService } from './time-signature.service';
import { SheetConfiguration } from '../model/sheet-configuration.model';
import { StaveConfiguration } from '../model/stave-configuration.model';

import { Track } from '../model/track.model';
import {
	MappedStave,
	mappedStaveInitializer,
	MappedBar,
	mappedBarInitializer,
	MappedClef,
	MappedKeySignature,
	MappedTimeSignature
} from '../model/stave.model';

@Injectable({
	providedIn: 'root'
})
export class StaveService implements ArrayCanvasService<Track, MappedStave> {

	constructor(
		private barService : BarService,
		private clefService : ClefService,
		private keySignatureService : KeySignatureService,
		private timeSignatureService : TimeSignatureService
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
			}, [this.emptyStave(source, staveConfig, sheetConfig, true)] as MappedStave[]);

		staves.forEach ((stave) => {
			let adjusted = this.barService.normalize(stave.bars, staveConfig.stavesWidth - stave.width);
			stave.width  = adjusted.width + stave.offset;
			stave.header = adjusted.header;
			stave.footer = adjusted.footer;
			stave.tracks = adjusted.tracks;
		});

		return staves;
	}

	private emptyStave (source : Track[], staveConfig : StaveConfiguration, sheetConfig : SheetConfiguration, first : boolean = false) : MappedStave {
		/* append clefs */

		let clefs = sheetConfig.showTracks.map(
			track => this.clefService.map(sheetConfig.clefArray[source[track].clef!!], staveConfig, sheetConfig)
		);

		let offset = staveConfig.stavesSpacing * 2 + clefs.reduce((total, clef) => clef.width > total ? clef.width : total, 0)

		/* append key signature */

		let keys  = sheetConfig.showTracks.map(
			track => this.keySignatureService.map(sheetConfig.keysArray[source[track].keySignature!!], staveConfig, sheetConfig)
		);

		let keysWidth = keys.reduce((total, key) => key.width > total ? key.width : total, 0);

		if (keysWidth > 0) {
			offset += (staveConfig.stavesSpacing + keysWidth);
		}

		/* append time signature */

		let times = [] as MappedTimeSignature[];

		if (first) {
			// only show time signature on first stave
			times = sheetConfig.showTracks.map(
				track => this.timeSignatureService.map(source[track].timeSignature!!, staveConfig, sheetConfig)
			);

			offset += (staveConfig.stavesSpacing + times.reduce((total, time) => time.width > total ? time.width : total, 0));
		}

		return {
			...mappedStaveInitializer(),
			header    : 0,
			offset    : offset,
			width     : offset,
			footer    : staveConfig.stavesLineHeight * 6,
			clefs     : clefs,
			times     : times,
			keys      : keys,
			keysWidth : keysWidth
		};
	}

	public draw (stave : MappedStave, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number, y : number) : void {
		x += staveConfig.stavesMargin;

		stave.tracks.forEach((track, i) => {
			this.setupStave(staveConfig, context, x, y + track);

			let clefx = x + staveConfig.stavesSpacing;
			this.clefService.draw(stave.clefs[i], staveConfig, context, clefx, y + track);

			let keysx = clefx;
			if (stave.keysWidth > 0) {
				keysx += (staveConfig.stavesSpacing + stave.clefs[i].width);
				this.keySignatureService.draw(stave.keys[i], staveConfig, context, keysx, y + track);
			}

			if (stave.times.length > 0) {
				let timex = keysx + staveConfig.stavesSpacing + stave.keys[i].width;
				this.timeSignatureService.draw(stave.times[i], staveConfig, context, timex, y + track);
			}
		});

		this.finishStave(stave, staveConfig, context, x, y);

		this.barService.draw (stave.bars, staveConfig, context, x + stave.offset, y);
	}

	private setupStave (staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number, y : number) : void {
		[-2, -1, 0, 1, 2].forEach(i => {
			let yline = staveConfig.stavesLineHeight * i + y;
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
		let top = stave.tracks[ 0 ] - 2 * staveConfig.stavesLineHeight;
		let end = stave.tracks[len] + 2 * staveConfig.stavesLineHeight;

		context.fillRect(x + staveConfig.stavesWidth - staveConfig.lineHeight, y + top, staveConfig.lineHeight, end - top);
	}

}
