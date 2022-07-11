import { Injectable } from '@angular/core';

import { Sheet } from './model/sheet.model';
import { Track } from './model/track.model';
import { Measure } from './model/measure.model';

import { Bar, Line } from './model/line.model';

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

	public lineOverhead : number = this.lineMargin * 2 + this.linePadding;

	public splitLines (sheet : Sheet) : Line[] {
		return this.splitBars (sheet).reduce ((lines : Line[], bar : Bar) : Line[] => {
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

	private splitBars (sheet : Sheet) : Bar[] {
		return sheet.tracks.reduce ((bars : Bar[], track : Track) : Bar[] => {
			console.log ("bars  : " + JSON.stringify(bars));
			console.log ("track : " + JSON.stringify(track));
			return track.measures.reduce ((bars : Bar[], measure : Measure) : Bar[] => {
				bars.push ({
					measure : measure,
					width   : this.barWidth(measure)
				});

				return bars;
			}, bars);
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

	public setupLine (context: CanvasRenderingContext2D, x : number, y : number, w : number, h : number, i : number) {
		var m : number = y + h / 2;
		var e : number = w - this.lineMargin;
		var s : number = x + this.lineMargin;

		w -= this.lineMargin * 2;

		context.fillStyle = 'black';
		context.fillRect(s, m - 20, w, 1);
		context.fillRect(s, m - 10, w, 1);
		context.fillRect(s, m     , w, 1);
		context.fillRect(s, m + 10, w, 1);
		context.fillRect(s, m + 20, w, 1);
		context.fillRect(e, m - 20, 1, 41);
	}

	public drawText (context: CanvasRenderingContext2D, x : number, y : number, w : number, h : number, text : string) {
		context.font = (h/2) + 'px consolas';
		context.textAlign = "center";
		context.fillText(text, x + w/2, y + 3*h/4 - 1);
	}
}
