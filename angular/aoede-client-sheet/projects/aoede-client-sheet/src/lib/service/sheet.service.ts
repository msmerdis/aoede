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
		var mappedSheet = {
			...mappedSheetInitializer(),
			mapped : true,
			width  : staveConfig.stavesWidth + 2 * staveConfig.stavesMargin
		} as MappedSheet;

		// append staves
		mappedSheet.staves = this.staveService.map (source.tracks, staveConfig, sheetConfig);
		mappedSheet.staves.forEach (stave => {
			mappedSheet.footer += (stave.header + stave.footer);
		});

		return mappedSheet;
	}

	public draw (sheet : MappedSheet, staveConfig : StaveConfiguration, context : CanvasRenderingContext2D, x : number = 0, y : number = 0) : void {
		console.log("draw : " + JSON.stringify(sheet));
		if (sheet.mapped) {
			let offset = sheet.header;
			let previousStype = context.fillStyle;
			context.fillStyle = 'black';

			sheet.staves.forEach (stave => {
				this.staveService.draw(stave, staveConfig, context, x, y + offset);
				offset += (stave.header + stave.footer);
			});

			context.fillStyle = previousStype;
		}
	}

}
