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
	private notes : string[] = ["C", "D", "E", "F", "G", "A", "B"];

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

		if (accidentalWidth > 0)
			accidentalWidth += staveConfig.lineHeight * 2;

		let dotCount = this.dots(note);

		return {
			...staveExtentionInitializer,
			header : offset,
			footer : staveConfig.stavesLineHeight - offset,
			width  : staveConfig.stavesLineHeight * 2 + accidentalWidth + dotCount * staveConfig.noteSpacing,
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
			- this.notes.indexOf(clef.clef.type)
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

		if (accidental < 0) {
			let header = staveConfig.noteSpacing;
			let footer = staveConfig.stavesLineHeight;

			return [staveConfig.noteSpacing + staveConfig.lineHeight*4, header, footer];
		}

		return [0, 0, 0];
	}

	public dots (note : Note) : number {
		if (note.flags) {
			return +note.flags['DOTTED'];
		}
		return 0;
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
		if (note.note.value > 2)
			context.fill();
		else
			context.stroke();

		let dots = this.dots(note.note);
		x += staveConfig.stavesLineHeight * 2;

		while (dots > 0) {
			context.beginPath();
			context.arc(x, y - note.header + staveConfig.stavesLineHeight/2, staveConfig.lineHeight * 2, 0, Math.PI * 2);
			context.fill()
			x += staveConfig.noteSpacing;
			dots -= 1;
		}

	}

	public drawAccidental (accidental : number, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number, y : number) : void {
		let [accidentalWidth, accidentalHeader, accidentalFooter] = this.mapAccidental(accidental, staveConfig);
/*
		context.save();
		context.fillStyle    = "yellow";
		context.fillRect(x, y - accidentalHeader, accidentalWidth, accidentalHeader + accidentalFooter);
		context.restore();
*/
		if (accidental > 0) {
			context.save();
			context.lineWidth = staveConfig.lineHeight * 2;
			context.fillRect(x + staveConfig.lineHeight * 2, y - accidentalHeader, staveConfig.lineHeight, accidentalHeader + accidentalFooter);
			context.fillRect(x - staveConfig.lineHeight * 3 + accidentalWidth, y - accidentalHeader, staveConfig.lineHeight, accidentalHeader + accidentalFooter);

			context.lineWidth = staveConfig.lineHeight * 3;
			[0, staveConfig.stavesLineHeight].forEach(offset => {
				context.beginPath();
				context.moveTo(x,                   y + offset + staveConfig.lineHeight);
				context.lineTo(x + accidentalWidth, y + offset - staveConfig.lineHeight);
				context.stroke();
			});
			context.restore();
		}

		if (accidental < 0) {
			context.save();
			context.beginPath();
			context.moveTo(x, y + accidentalFooter);
			context.lineTo(x + accidentalWidth/2, y - accidentalHeader);
			context.lineTo(x + staveConfig.lineHeight + accidentalWidth /2, y - accidentalHeader);
			context.lineTo(x + staveConfig.lineHeight, y + accidentalFooter);
			context.fill();
			context.beginPath();
			context.moveTo(x, y + accidentalFooter);
			context.quadraticCurveTo(x + accidentalWidth, y + accidentalFooter/2, x + accidentalWidth/4, y - accidentalHeader/2 + accidentalFooter/2);
			context.stroke();
			context.restore();
		}
	}
}
