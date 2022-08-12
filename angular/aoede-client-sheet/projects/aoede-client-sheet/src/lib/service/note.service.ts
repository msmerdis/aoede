import { Injectable } from '@angular/core';

import { ArrayCanvasService } from './canvas.service';
import { SheetConfiguration } from '../model/sheet-configuration.model';
import { StaveConfiguration } from '../model/stave-configuration.model';

import { Note } from '../model/note.model';
import { Clef } from '../model/clef.model';
import { NoteOffset } from '../model/key-signature.model';
import { StaveMapState, staveMapStateInitializer } from '../model/stave-map-state.model';
import {
	MappedClef,
	MappedNote,
	staveExtentionInitializer
} from '../model/stave.model';

@Injectable({
	providedIn: 'root'
})
export class NoteService implements ArrayCanvasService<Note, MappedNote> {

	constructor() { }

	public map  (source : Note[], staveConfig : StaveConfiguration, sheetConfig : SheetConfiguration, staveState : StaveMapState = staveMapStateInitializer()): MappedNote[] {
		let clef  = staveState.mappedClef;
		let notes = staveState.mappedKey.keySignature.notes || [];

		return source.map(
			(note) => this.mapNote(note, staveConfig, sheetConfig, clef, notes)
		);
	}

	private mapNote (note : Note, staveConfig : StaveConfiguration, sheetConfig : SheetConfiguration, clef : MappedClef, notes : NoteOffset[]): MappedNote {
		let offset     = -staveConfig.lineHeight/2;
		let accidental = 0;

		if (note.pitch >= 0) {
			let adjustment;

			[adjustment, accidental] = this.calculateNoteOffset(note, clef, notes);

			offset += adjustment * staveConfig.stavesLineHeight / 2;
		}

		return {
			...staveExtentionInitializer,
			header : staveConfig.stavesLineHeight / 2 + offset,
			footer : staveConfig.stavesLineHeight / 2 - offset,
			width  : staveConfig.noteSpacing * 16,
			note   : note,
			accidental : accidental
		};
	}

	private calculateNoteOffset (note : Note, clef : MappedClef, notes : NoteOffset[]): [number, number] {
		let [octave, offset] = this.explodePitch(note.pitch);

		return [
			(octave - clef.octave) * 7
			+ notes[offset].offset
			- notes[clef.note].offset
			+ clef.clef.spos,
			notes[offset].accidental
		];
	}

	public explodePitch (pitch : number) : [number, number] {
		let div = Math.floor(pitch / 12);
		let rem = pitch - (12 * div);

		return [div, rem];
	}

	public draw (note : MappedNote, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number, y : number) : void {
		context.fillRect(x, y - note.header, note.width, note.header + note.footer);
		context.save();
		context.font         = "bold " + ((note.header + note.footer) * 1.2) + "px ''";
		context.textAlign    = "center";
		context.textBaseline = "alphabetic";
		context.fillStyle    = "white";
		context.fillText(note.note.pitch + " - " + (note.accidental) + " - " + note.note.value.numerator + "/" + note.note.value.denominator, x + (note.width / 2), y + note.footer);
		context.restore();
	}
}
