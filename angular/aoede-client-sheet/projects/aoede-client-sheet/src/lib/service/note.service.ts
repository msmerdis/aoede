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
			(note) => note.pitch < 0
				? this.mapRest(note, staveConfig, sheetConfig)
				: this.mapNote(note, staveConfig, sheetConfig, clef, notes)
		);
	}

	private mapNote (note : Note, staveConfig : StaveConfiguration, sheetConfig : SheetConfiguration, clef : MappedClef, notes : NoteOffset[]): MappedNote {
		let [adjustment, accidental] = this.calculateNoteOffset(note.pitch, clef, notes);
		let offset = staveConfig.noteSpacing + adjustment * staveConfig.stavesLineHeight / 2;

		let [accidentalWidth, accidentalHeader, accidentalFooter] = this.mapAccidental(accidental, staveConfig);

		if (accidentalWidth > 0)
			accidentalWidth += staveConfig.lineHeight * 2;

		let dotCount = this.dots(note);

		let stemHeight = 0;

		if (note.value > 1) {
			stemHeight = staveConfig.stavesLineHeight * 3;
		}

		return {
			...staveExtentionInitializer,
			header : offset + stemHeight,
			footer : staveConfig.stavesLineHeight - offset,
			width  : staveConfig.stavesLineHeight * 2 + accidentalWidth + dotCount * staveConfig.noteSpacing,
			note   : note,
			center : offset - staveConfig.stavesLineHeight / 2,
			offset : accidentalWidth,
			adjustment : adjustment,
			accidental : accidental
		};
	}

	private mapRest (note : Note, staveConfig : StaveConfiguration, sheetConfig : SheetConfiguration): MappedNote {
		switch (note.value) {
			case 1: return {
				...staveExtentionInitializer,
				header : staveConfig.stavesLineHeight,
				footer :-staveConfig.stavesLineHeight / 2,
				width  : staveConfig.stavesLineHeight,
				note   : note,
				center : 0,
				offset : 0,
				adjustment : 0,
				accidental : 0
			};
			case 2: return {
				...staveExtentionInitializer,
				header : staveConfig.stavesLineHeight / 2,
				footer : 0,
				width  : staveConfig.stavesLineHeight,
				note   : note,
				center : 0,
				offset : 0,
				adjustment : 0,
				accidental : 0
			};
			case 4: return {
				...staveExtentionInitializer,
				header : staveConfig.stavesLineHeight * 5 / 4,
				footer : staveConfig.stavesLineHeight * 5 / 4,
				width  : staveConfig.stavesLineHeight,
				note   : note,
				center : 0,
				offset : 0,
				adjustment : 0,
				accidental : 0
			};
			default: return {
				...staveExtentionInitializer,
				header : staveConfig.stavesLineHeight / 2,
				footer : staveConfig.stavesLineHeight / 2,
				width  : staveConfig.stavesLineHeight,
				note   : note,
				center : 0,
				offset : 0,
				adjustment : 0,
				accidental : 0
			};
		}
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
			let header = staveConfig.stavesLineHeight / 2 + staveConfig.lineHeight * 4;
			let footer = header;

			return [staveConfig.noteSpacing + staveConfig.lineHeight*4, header, footer];
		}

		if (accidental < 0) {
			let footer = staveConfig.stavesLineHeight/2;
			let header = staveConfig.noteSpacing + footer;

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
		if (note.note.pitch < 0) {
			this.drawRest(note, staveConfig, context, x, y);
		} else {
			this.drawNote(note, staveConfig, context, x, y);
		}
	}

	public drawNote (note : MappedNote, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number, y : number) : void {
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

		this.drawAccidental(note.accidental, staveConfig, context, x + staveConfig.noteSpacing, y - note.center);
		x += note.offset;

		context.beginPath();
		context.ellipse(x + staveConfig.stavesLineHeight, y - note.center, staveConfig.noteSpacing, staveConfig.noteSpacing + staveConfig.lineHeight, Math.PI * .4, 0, Math.PI * 2);
		if (note.note.value > 2)
			context.fill();
		else
			context.stroke();

		if (note.note.value > 1) {
			let stemX = x + staveConfig.stavesLineHeight + staveConfig.noteSpacing + staveConfig.lineHeight;
			context.beginPath();
			context.moveTo(stemX, y - note.header);
			context.lineTo(stemX, y - note.center);
			context.stroke();
		}

		let dots = this.dots(note.note);
		x += staveConfig.stavesLineHeight * 2;

		while (dots > 0) {
			context.beginPath();
			context.arc(x, y - note.center, staveConfig.lineHeight * 2, 0, Math.PI * 2);
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
			[-staveConfig.stavesLineHeight/2, staveConfig.stavesLineHeight/2].forEach(offset => {
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
			context.quadraticCurveTo(x + accidentalWidth, y - accidentalHeader/2 + accidentalFooter/2, x + accidentalWidth/4, y - accidentalHeader/2 + accidentalFooter/2);
			context.stroke();
			context.restore();
		}
	}

	public drawRest (note : MappedNote, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number, y : number) : void {
		/*
		context.save();
		context.fillStyle    = "white";
		context.fillRect(x, y - note.header, note.width, note.header + note.footer);
		context.restore();
		*/

		switch (note.note.value) {
		case 1:
		case 2:
			context.fillRect(x, y - note.header, note.width, note.header + note.footer);
			break;
		case 4:
			context.save();
			context.translate(x, y - note.header);
			context.scale(staveConfig.scale / 2, staveConfig.scale / 2);
			context.beginPath();
			context.moveTo(4,2);
			context.bezierCurveTo(5.5,3.6,17,17.7,17,17.7);
			context.bezierCurveTo(17,17.7,10.6,23.799999999999997,10.6,30.2);
			context.bezierCurveTo(10.6,37.7,19.2,44.5,19.2,44.5);
			context.lineTo(18.3,45.6);
			context.bezierCurveTo(15,43.7,9.4,43.5,6.9,46.4);
			context.bezierCurveTo(3.8000000000000003,50,10.8,55.5,10.8,55.5);
			context.lineTo(10,56.6);
			context.bezierCurveTo(7.6,54.800000000000004,-2.5999999999999996,45.2,1.6999999999999993,40.5);
			context.bezierCurveTo(4.299999999999999,37.6,7.499999999999999,36.7,12,39.1);
			context.lineTo(-0.09999999999999964,26.6);
			context.bezierCurveTo(6.9,18,8.1,15.500000000000002,8.1,13.200000000000001);
			context.bezierCurveTo(8.1,8.400000000000002,4.699999999999999,5.000000000000002,3,2.8000000000000007);
			context.bezierCurveTo(2.4,1.9000000000000008,1.3,1.2000000000000006,2,0.6000000000000005);
			context.bezierCurveTo(2.7,0.10000000000000053,3.1,0.9000000000000006,4,2.0000000000000004);
			context.closePath();
			context.fill();
			context.stroke();
			context.restore();
			break;
		default:
			context.save();
			context.fillStyle    = "white";
			context.fillRect(x, y - note.header, note.width, note.header + note.footer);
			context.restore();

			context.save();
			context.textAlign = 'center';
			context.textBaseline = 'middle';
			context.fillText("?", x + note.width/2, y - note.header/2 + note.footer/2);
			context.restore();
		}
	}

}
