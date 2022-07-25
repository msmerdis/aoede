import { Injectable } from '@angular/core';

import { ArrayCanvasService } from './canvas.service';
import { SheetConfiguration } from '../model/sheet-configuration.model';
import { StaveConfiguration } from '../model/stave-configuration.model';

import { Note } from '../model/note.model';
import { Clef, clefInitializer } from '../model/clef.model';
import { MappedNote, staveExtentionInitializer } from '../model/stave.model';

@Injectable({
	providedIn: 'root'
})
export class NoteService implements ArrayCanvasService<Note, MappedNote> {

	constructor() { }

	public map  (source : Note[], staveConfig : StaveConfiguration, sheetConfig : SheetConfiguration, clef : Clef = clefInitializer()): MappedNote[] {
		return source.map(
			(note) => {
				return {
					...staveExtentionInitializer,
					header : staveConfig.noteSpacing,// + (note.pitch - clef.note) * staveConfig.stavesHalfHeight,
					footer : staveConfig.noteSpacing,// + (note.pitch - clef.note) * staveConfig.stavesHalfHeight,
					width  : staveConfig.noteSpacing * 2,
					note   : note
				};
			}
		);
	}

	public draw (stave : MappedNote, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number, y : number) : void {
		context.fillRect(x, y - stave.header, stave.width, stave.header + stave.footer);
	}
}
