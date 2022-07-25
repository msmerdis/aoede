import { Injectable } from '@angular/core';

import { ArrayCanvasService } from './canvas.service';
import { ClefService } from './clef.service';
import { KeySignatureService } from './key-signature.service';
import { TimeSignatureService } from './time-signature.service';
import { SheetConfiguration } from '../model/sheet-configuration.model';
import { StaveConfiguration } from '../model/stave-configuration.model';

import { StaveSignature } from '../model/stave-signature.model';
import {
	MappedStaveSignature,
	mappedStaveSignatureInitializer,
	MappedClef,
	MappedKeySignature,
	MappedTimeSignature
} from '../model/stave.model';

@Injectable({
	providedIn: 'root'
})
export class StaveSignatureService implements ArrayCanvasService<StaveSignature, MappedStaveSignature> {

	constructor(
		private clefService : ClefService,
		private keySignatureService : KeySignatureService,
		private timeSignatureService : TimeSignatureService
	) {}

	public map (source : StaveSignature[], staveConfig : StaveConfiguration, sheetConfig : SheetConfiguration, showTime : boolean = false) : MappedStaveSignature[] {
		return source.map(
			(track) => {
				let stave = mappedStaveSignatureInitializer();

				if (track.clef) {
					stave.clef   = this.clefService.map(sheetConfig.clefArray[track.clef], staveConfig, sheetConfig);
					stave.header = stave.clef.header;
					stave.footer = stave.clef.footer;

					stave.timeOffset = stave.keyOffset = stave.clef.width + staveConfig.stavesSpacing;
				}

				if (track.keySignature) {
					stave.key = this.keySignatureService.map(sheetConfig.keysArray[track.keySignature], staveConfig, sheetConfig);

					if (stave.key.width > 0) {
						stave.header = Math.max(stave.header, stave.clef.header);
						stave.footer = Math.max(stave.header, stave.clef.footer);

						stave.timeOffset += stave.key.width + staveConfig.stavesSpacing;
					}
				}

				if (track.timeSignature && showTime) {
					stave.time       = this.timeSignatureService.map(track.timeSignature, staveConfig, sheetConfig);
					stave.timeOffset = stave.keyOffset + staveConfig.stavesSpacing + stave.key.width;
					stave.header     = Math.max(stave.header, stave.time.header);
					stave.footer     = Math.max(stave.header, stave.time.footer);
					stave.width      = stave.timeOffset + staveConfig.stavesSpacing + stave.time.width;
				} else {
					stave.width      = stave.keyOffset;
				}

				return stave;
			}
		);
	}

	public draw (target : MappedStaveSignature, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number, y : number) : void {

		if (target.clef.width > 0) {
			this.clefService.draw(target.clef, staveConfig, context, x + target.clefOffset, y);
		}

		if (target.key.width > 0) {
			this.keySignatureService.draw(target.key, staveConfig, context, x + target.keyOffset, y);
		}

		if (target.time.width > 0) {
			this.timeSignatureService.draw(target.time, staveConfig, context, x + target.timeOffset, y);
		}

	}

}
