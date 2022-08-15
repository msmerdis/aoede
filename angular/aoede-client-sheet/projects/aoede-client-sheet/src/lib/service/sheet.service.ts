import { Injectable } from '@angular/core';

import { SingleCanvasService } from './canvas.service';
import { StaveService } from './stave.service';
import { SheetConfiguration } from '../model/sheet-configuration.model';
import { StaveConfiguration } from '../model/stave-configuration.model';

import { Sheet } from '../model/sheet.model';
import { MappedSheet, mappedSheetInitializer } from '../model/stave.model';

@Injectable({
	providedIn: 'root'
})
export class SheetService implements SingleCanvasService<Sheet, MappedSheet> {

	constructor(
		private staveService : StaveService
	) {}

	public map  (source : Sheet, staveConfig : StaveConfiguration, sheetConfig : SheetConfiguration): MappedSheet {
		let margin = 2 * staveConfig.stavesMargin;
		let mappedSheet = {
			...mappedSheetInitializer(),
			mapped : true,
			width  : staveConfig.stavesWidth + margin
		} as MappedSheet;

		// append staves
		mappedSheet.staves = this.staveService.map (source.tracks, staveConfig, sheetConfig);
		mappedSheet.staves.forEach (stave => {
			mappedSheet.footer += (stave.header + stave.footer);
			mappedSheet.inner   = Math.max(mappedSheet.inner, stave.width);
		});

		mappedSheet.footer *= this.calculateSheetScale(mappedSheet, margin);

		return mappedSheet;
	}

	private calculateSheetScale (sheet : MappedSheet, margin : number) {
		return Math.min(sheet.width / (sheet.inner + margin), 1);
	}

	public draw (sheet : MappedSheet, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number = 0, y : number = 0) : void {
		if (sheet.mapped) {
			let offset = sheet.header;
			let previousStype = context.fillStyle;
			let scale = this.calculateSheetScale(sheet, 2 * staveConfig.stavesMargin);

			context.save();
			context.scale(scale, scale);
			context.fillStyle = 'black';
			context.lineWidth = staveConfig.lineHeight;

			sheet.staves.forEach (stave => {
				this.staveService.draw(stave, staveConfig, context, x, y + offset, Math.min(staveConfig.stavesWidth / sheet.inner, 1));
				offset += (stave.header + stave.footer);
			});

			context.fillStyle = previousStype;
			context.restore();
		}
	}

}
