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
		let offset     = staveConfig.noteSpacing;
		let accidental = 0;
		let adjustment = 0;

		if (note.pitch >= 0) {
			[adjustment, accidental] = this.calculateNoteOffset(note, clef, notes);

			offset += adjustment * staveConfig.stavesLineHeight / 2;
		}

		return {
			...staveExtentionInitializer,
			header : offset,
			footer : staveConfig.stavesLineHeight - offset,
			width  : staveConfig.stavesLineHeight * 2,
			note   : note,
			adjustment : adjustment,
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
/*
		context.save();
		context.fillStyle    = "white";
		context.fillRect(x, y - note.header, note.width, note.header + note.footer);
		context.restore();
*/
		for (let i = 3; i <= note.adjustment/2; i += 1) {
			console.log("draw line " + i);
			let yline = staveConfig.stavesLineHeight * -i + y;
			context.fillRect(
				x,
				yline,
				note.width,
				staveConfig.lineHeight
			);
		}

		for (let i = -3; i >= note.adjustment/2; i -= 1) {
			console.log("draw line " + i);
			let yline = staveConfig.stavesLineHeight * -i + y;
			context.fillRect(
				x,
				yline,
				note.width,
				staveConfig.lineHeight
			);
		}

		context.beginPath();
		context.strokeStyle = "5px";
		context.ellipse(x + staveConfig.stavesLineHeight, y - note.header + staveConfig.stavesLineHeight/2, staveConfig.noteSpacing, staveConfig.noteSpacing + staveConfig.lineHeight, Math.PI * .4, 0, Math.PI * 2);
		context.stroke();
	}
}
