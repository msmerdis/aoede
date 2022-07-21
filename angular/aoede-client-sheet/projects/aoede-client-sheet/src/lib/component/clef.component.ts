import { Component, ViewChild, ElementRef, Input, OnInit, OnChanges } from '@angular/core';

import { SheetConfiguration, sheetConfigurationInitializer } from '../model/sheet-configuration.model';
import { StaveConfiguration, staveConfigurationInitializer } from '../model/stave-configuration.model';

import { Clef, clefInitializer } from '../model/clef.model';
import { MappedClef, mappedClefInitializer } from '../model/stave.model';

import { ClefService } from '../service/clef.service';

@Component({
	selector: 'aoede-sheet-clef',
	template: '<canvas #cCanvas width="{{ mappedClef.width }}" height="{{ mappedClef.header + mappedClef.footer }}"></canvas>'
})
export class ClefComponent implements OnInit, OnChanges {

	@Input() clef: string = "";

	private context : CanvasRenderingContext2D | null = null;

	@ViewChild('cCanvas') set content (content: ElementRef) {
		if (content != null) {
			this.context = content.nativeElement.getContext('2d');
			this.drawCanvas();
		}
	}

	public mappedClef : MappedClef = mappedClefInitializer();

	private staveConfig : StaveConfiguration = staveConfigurationInitializer(1);
	private sheetConfig : SheetConfiguration = sheetConfigurationInitializer( );

	constructor(
		private clefService : ClefService
	) {}

	ngOnInit (): void {
		this.ngOnChanges();
	}

	ngOnChanges (): void {
		this.mappedClef = this.clefService.map(clefInitializer(this.clef), this.staveConfig, this.sheetConfig);
		this.drawCanvas();
	}

	private drawCanvas () {
		console.log("attempt to draw");
		if (this.context !== null) {
			this.context.save();
			this.context.clearRect(
				0, 0,
				this.mappedClef.width,
				this.mappedClef.header + this.mappedClef.footer
			);
			console.log("todraw");
			this.clefService.draw (
				this.mappedClef,
				this.staveConfig,
				this.context,
				0,
				this.mappedClef.header
			);
			console.log("drawn");
			this.context.restore();
		}
	}

}
