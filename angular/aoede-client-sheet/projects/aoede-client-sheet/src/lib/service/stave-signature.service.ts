import { Injectable } from '@angular/core';

import { SingleCanvasService } from './canvas.service';
import { ClefService } from './clef.service';
import { KeySignatureService } from './key-signature.service';
import { TimeSignatureService } from './time-signature.service';
import { SheetConfiguration } from '../model/sheet-configuration.model';
import { StaveConfiguration } from '../model/stave-configuration.model';

import { timeSignatureBeats } from '../model/time-signature.model';
import { StaveSignature } from '../model/stave-signature.model';
import { StaveMapState, staveMapStateInitializer } from '../model/stave-map-state.model';
import {
	MappedStaveSignature,
	mappedStaveSignatureInitializer
} from '../model/stave.model';

@Injectable({
	providedIn: 'root'
})
export class StaveSignatureService implements SingleCanvasService<StaveSignature, MappedStaveSignature> {

	constructor(
		private clefService : ClefService,
		private keySignatureService : KeySignatureService,
		private timeSignatureService : TimeSignatureService
	) {}

	public initialize (source : StaveSignature, staveConfig : StaveConfiguration, sheetConfig : SheetConfiguration) : StaveMapState {
		let stave = staveMapStateInitializer(
			source,
			sheetConfig.timesList
		);

		this.updateState(source, staveConfig, sheetConfig, stave);

		return stave;
	}

	public map (source : StaveSignature, staveConfig : StaveConfiguration, sheetConfig : SheetConfiguration, staveState : StaveMapState = staveMapStateInitializer()) : MappedStaveSignature {
		let stave = mappedStaveSignatureInitializer();

		let showKey  = source.keySignature  !== undefined && (staveState.keySignature  === undefined || staveState.keySignature === source.keySignature);
		let showTime = source.timeSignature !== undefined && (staveState.timeSignature === undefined || (
			staveState.timeSignature.numerator   === source.timeSignature.numerator &&
			staveState.timeSignature.denominator === source.timeSignature.denominator
		));

		this.updateState(source, staveConfig, sheetConfig, staveState);

		stave.key  = staveState.mappedKey;
		stave.time = staveState.mappedTime;

		stave.separator = staveConfig.noteSpacing;

		this.updateSignature(stave, showKey, showTime);

		return stave;
	}

	private updateState (source : StaveSignature, staveConfig : StaveConfiguration, sheetConfig : SheetConfiguration, staveState : StaveMapState) : void {
		if (source.clef !== undefined) {
			staveState.clef       = source.clef;
			staveState.mappedClef = this.clefService.map(sheetConfig.clefArray[source.clef], staveConfig, sheetConfig);
		}

		if (source.keySignature !== undefined) {
			staveState.keySignature = source.keySignature;
			staveState.mappedKey = this.keySignatureService.map(sheetConfig.keysArray[source.keySignature], staveConfig, sheetConfig);
		}

		if (source.timeSignature !== undefined) {
			staveState.timeSignature = source.timeSignature;
			staveState.mappedTime = this.timeSignatureService.map(source.timeSignature, staveConfig, sheetConfig);
			staveState.beats = timeSignatureBeats(source.timeSignature, sheetConfig.timesList);
		}
	}

	public updateSignature (signature : MappedStaveSignature, showKey : boolean, showTime : boolean) {
		if (signature.showKey == false && showKey == true) {
			if (signature.width > 0) {
				signature.width += signature.separator;
			}
			signature.width += signature.key.width;
			signature.header = Math.max(signature.header, signature.key.header);
			signature.footer = Math.max(signature.footer, signature.key.footer);

			signature.showKey = true;
		}

		if (signature.showTime == false && showTime == true) {
			if (signature.width > 0) {
				signature.width += signature.separator;
			}
			signature.width += signature.time.width;
			signature.header = Math.max(signature.header, signature.time.header);
			signature.footer = Math.max(signature.footer, signature.time.footer);

			signature.showTime = true;
		}
	}

	public draw (target : MappedStaveSignature, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number, y : number) : void {
		if (target.showKey) {
			this.keySignatureService.draw(target.key, staveConfig, context, x, y);
			x += (target.key.width + target.separator);
		}

		if (target.showTime) {
			this.timeSignatureService.draw(target.time, staveConfig, context, x, y);
		}

	}

}
