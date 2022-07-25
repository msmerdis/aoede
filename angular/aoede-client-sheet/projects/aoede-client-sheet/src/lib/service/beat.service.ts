import { Injectable } from '@angular/core';

import { SingleCanvasService } from './canvas.service';
import { NoteService } from './note.service';
import { SheetConfiguration } from '../model/sheet-configuration.model';
import { StaveConfiguration } from '../model/stave-configuration.model';

import { Measure } from '../model/measure.model';
import { Clef, clefInitializer } from '../model/clef.model';
import { MappedBeat, mappedBeatInitializer } from '../model/stave.model';

@Injectable({
	providedIn: 'root'
})
export class BeatService implements SingleCanvasService<Measure, MappedBeat[]> {

	constructor(
		private noteService : NoteService
	) { }

	public map  (source : Measure, staveConfig : StaveConfiguration, sheetConfig : SheetConfiguration, beats : number[] = [0], clef : Clef = clefInitializer()): MappedBeat[] {
		return [];
	}

	public draw (target : MappedBeat[], staveConfig : StaveConfiguration, context : CanvasRenderingContext2D) : void {
	}

}
