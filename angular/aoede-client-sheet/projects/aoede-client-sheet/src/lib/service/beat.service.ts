import { Injectable } from '@angular/core';

import { MultiCanvasService } from './canvas.service';
import { NoteService } from './note.service';
import { SheetConfiguration } from '../model/sheet-configuration.model';
import { StaveConfiguration } from '../model/stave-configuration.model';

import { Measure } from '../model/measure.model';
import { Clef, clefInitializer } from '../model/clef.model';
import { MappedBeat, mappedBeatInitializer } from '../model/stave.model';

@Injectable({
	providedIn: 'root'
})
export class BeatService implements MultiCanvasService<Measure, MappedBeat> {

	constructor(
		private noteService : NoteService
	) { }

	public map  (source : Measure, staveConfig : StaveConfiguration, sheetConfig : SheetConfiguration, beats : number[] = [0], clef : Clef = clefInitializer()): MappedBeat[] {
		let mappedBeat = mappedBeatInitializer();
		let notes = this.noteService.map(source.notes, staveConfig, sheetConfig, clef);

		mappedBeat.separator = staveConfig.noteSpacing;
		mappedBeat.notes     = notes;
		mappedBeat.notes.forEach(note => {
			mappedBeat.width += (mappedBeat.width > 0 ? note.width + mappedBeat.separator : note.width);
			mappedBeat.header = Math.max(mappedBeat.header, note.header);
			mappedBeat.footer = Math.max(mappedBeat.footer, note.footer);
		});

		return [mappedBeat];
	}

	public draw (target : MappedBeat, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number, y : number) : void {
		target.notes.forEach(note => {
			this.noteService.draw(note, staveConfig, context, x, y);
			x += (note.width + target.separator);
		});
	}

	public normalize (target : MappedBeat, excess : number) : void {
		target.separator += Math.floor((excess + target.notes.length - 2) / (target.notes.length - 1));
	}

}
