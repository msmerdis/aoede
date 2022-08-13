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
			[adjustment, accidental] = this.calculateNoteOffset(note.pitch, clef, notes);

			offset += adjustment * staveConfig.stavesLineHeight / 2;
		}

		let [accidentalWidth, accidentalHeader, accidentalFooter] = this.mapAccidental(accidental, staveConfig);

		return {
			...staveExtentionInitializer,
			header : offset, //Math.max(accidentalHeader, offset),
			footer : staveConfig.stavesLineHeight - offset, //Math.max(accidentalFooter, staveConfig.stavesLineHeight - offset),
			width  : staveConfig.stavesLineHeight * 2 + accidentalWidth,
			note   : note,
			offset : accidentalWidth,
			adjustment : adjustment,
			accidental : accidental
		};
	}

	public calculateNoteOffset (pitch : number, clef : MappedClef, notes : NoteOffset[]): [number, number] {
		let [octave, offset] = this.explodePitch(pitch);

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

	public mapAccidental (accidental : number, staveConfig : StaveConfiguration) : [number, number, number] {
		if (accidental > 0) {
			let header = staveConfig.lineHeight * 4;
			let footer = staveConfig.stavesLineHeight + header;

			return [staveConfig.noteSpacing + staveConfig.lineHeight*4, header, footer];
		}

		if (accidental < 0)
			return [staveConfig.stavesLineHeight * 2, 0, staveConfig.stavesLineHeight];

		return [0, 0, 0];
	}

	public draw (note : MappedNote, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number, y : number) : void {
/*
		context.save();
		context.fillStyle    = "white";
		context.fillRect(x, y - note.header, note.width, note.header + note.footer);
		context.restore();
*/

		for (let i = 3; i <= note.adjustment/2; i += 1) {
			let yline = staveConfig.stavesLineHeight * -i + y;
			context.fillRect(
				x,
				yline,
				note.width,
				staveConfig.lineHeight
			);
		}

		for (let i = -3; i >= note.adjustment/2; i -= 1) {
			let yline = staveConfig.stavesLineHeight * -i + y;
			context.fillRect(
				x,
				yline,
				note.width,
				staveConfig.lineHeight
			);
		}

		this.drawAccidental(note.accidental, staveConfig, context, x + staveConfig.noteSpacing, y - note.header);
		x += note.offset;

		context.beginPath();
		context.ellipse(x + staveConfig.stavesLineHeight, y - note.header + staveConfig.stavesLineHeight/2, staveConfig.noteSpacing, staveConfig.noteSpacing + staveConfig.lineHeight, Math.PI * .4, 0, Math.PI * 2);
		context.stroke();
	}

	public drawAccidental (accidental : number, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number, y : number) : void {
		let [accidentalWidth, accidentalHeader, accidentalFooter] = this.mapAccidental(accidental, staveConfig);
/*
		context.save();
		context.fillStyle    = "yellow";
		context.fillRect(x + staveConfig.noteSpacing, y - accidentalHeader, accidentalWidth, accidentalHeader + accidentalFooter);
		context.restore();
*/
		if (accidentalWidth > 0) {
			context.fillRect(x + staveConfig.lineHeight * 2, y - accidentalHeader, staveConfig.lineHeight, accidentalHeader + accidentalFooter);
			context.fillRect(x - staveConfig.lineHeight * 3 + accidentalWidth, y - accidentalHeader, staveConfig.lineHeight, accidentalHeader + accidentalFooter);

			[0, staveConfig.stavesLineHeight].forEach(offset => {
				context.moveTo(x,                   y + offset + staveConfig.lineHeight);
				context.lineTo(x + accidentalWidth, y + offset - staveConfig.lineHeight);
				context.stroke();
			});

		}
	}
}
