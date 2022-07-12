import { Injectable } from '@angular/core';

import { Clef } from './model/clef.model';
import { Sheet } from './model/sheet.model';
import { Track } from './model/track.model';
import { Measure } from './model/measure.model';

import { Bar, Line } from './model/line.model';
import { TrackInfo } from './model/track-info.model';

@Injectable({
	providedIn: 'root'
})
export class MusicCanvasService {

	public lineMargin   : number =  11 * 5;
	public linePadding  : number =  11 * 5;
	public lineWidth    : number = 210 * 5;
	public lineHeight   : number =  33 * 5;
	public headerHeight : number =  22 * 5;
	public footerHeight : number =  11 * 5;
	public noteSpacing  : number =       5;

	public lineOverhead : number = this.lineMargin * 2 + this.linePadding;

	public splitLines (track : Track) : Line[] {
		return this.splitBars (track).reduce ((lines : Line[], bar : Bar) : Line[] => {
			var bottom = lines[lines.length - 1];

			if (bottom.width + bar.width > this.lineWidth) {
				bottom = this.emptyLine();
				lines.push(bottom);
			}

			bottom.width += bar.width;
			bottom.bars.push (bar);

			return lines;
		}, [this.emptyLine()] as Line[]);
	}

	private splitBars (track : Track) : Bar[] {
		return track.measures.reduce ((bars : Bar[], measure : Measure) : Bar[] => {
			bars.push ({
				measure : measure,
				width   : this.barWidth(measure)
			});

			return bars;
		}, [] as Bar[]);
	}

	private barWidth (measure : Measure) : number {
		return 200;
	}

	private emptyLine () : Line {
		return {
			bars  : [],
			width : this.lineOverhead
		};
	}

	public clearArea (context: CanvasRenderingContext2D, x : number, y : number, w : number, h : number) {
		context.clearRect(x, y, w, h);
	}

	public setupLine (context: CanvasRenderingContext2D, s : number, e : number, m : number, w : number) {
		context.fillStyle = 'black';
		context.fillRect(s, m - this.noteSpacing * 4, w, 1);
		context.fillRect(s, m - this.noteSpacing * 2, w, 1);
		context.fillRect(s, m                       , w, 1);
		context.fillRect(s, m + this.noteSpacing * 2, w, 1);
		context.fillRect(s, m + this.noteSpacing * 4, w, 1);
		context.fillRect(e, m - this.noteSpacing * 4, 1, this.noteSpacing * 8 + 1);
	}

	public drawTitle (context: CanvasRenderingContext2D, x : number, y : number, w : number, h : number, text : string) {
		context.font = (h/2) + 'px consolas';
		context.textAlign = "center";
		context.fillStyle = 'black';
		context.fillText(text, x + w/2, y + 3*h/4 - 1);
	}

	public drawLine (context: CanvasRenderingContext2D, x : number, y : number, w : number, h : number, info : TrackInfo, line : Line) {
		var m : number = y + h / 2;
		var e : number = w - this.lineMargin;
		var s : number = x + this.lineMargin;

		w -= this.lineMargin * 2;

		this.setupLine (context, s, e, m, w);
		this.drawClef  (context, s + this.noteSpacing * 4 + 1, m, info.clef);
	}

	public drawClef (context: CanvasRenderingContext2D, x : number, y : number, clef : Clef) {
		context.fillStyle = 'yellow';
		context.fillRect(x - this.noteSpacing * 2, y - this.noteSpacing * (2 + clef.spos), this.noteSpacing * 4, this.noteSpacing * 4);
	}

}
