import { Component, ViewChild, ChangeDetectorRef, ElementRef, Input, Output, EventEmitter, OnInit, OnDestroy, OnChanges } from '@angular/core';
import { Subscription, Observable, of, combineLatest, throwError } from 'rxjs';
import { tap, map, take, filter, switchMap } from 'rxjs/operators';

import { Sheet, sheetInitializer } from '../model/sheet.model';
import { SheetConfiguration, sheetConfigurationInitializer } from '../model/sheet-configuration.model';
import { StaveConfiguration, staveConfigurationInitializer } from '../model/stave-configuration.model';
import { MappedSheet, mappedSheetInitializer } from '../model/stave.model';

import { SheetService } from '../service/sheet.service';

@Component({
	selector: 'aoede-sheet',
	templateUrl: './sheet.component.html',
	styleUrls: ['./sheet.component.scss']
})
export class SheetComponent implements OnInit, OnChanges, OnDestroy {

	@Input() sheet       : Sheet              = sheetInitializer();
	@Input() sheetConfig : SheetConfiguration = sheetConfigurationInitializer();
	@Input() staveConfig : StaveConfiguration = staveConfigurationInitializer();

	public mappedSheet : MappedSheet = mappedSheetInitializer();

	private context  : CanvasRenderingContext2D | null = null;

	@ViewChild('sCanvas') set content (content: ElementRef) {
		if (content != null) {
			this.context = content.nativeElement.getContext('2d');
			//this.drawCanvas();
		}
	}

	constructor(
		private sheetService      : SheetService,
		private changeDetectorRef : ChangeDetectorRef
	) {}

	ngOnInit () : void {
	}

	ngOnDestroy (): void {
	}

	ngOnChanges (): void {
		this.mappedSheet = this.sheetService.map(this.sheet, this.staveConfig, this.sheetConfig);
		this.changeDetectorRef.detectChanges();
		this.drawCanvas();
	}

	private drawCanvas () {
		if (this.context !== null && this.mappedSheet.mapped) {
			this.context.save();
			this.context.clearRect(0, 0, this.mappedSheet.width, this.mappedSheet.footer);
			this.sheetService.draw (
				this.mappedSheet,
				this.staveConfig,
				this.context
			);
			this.context.restore();
		}
	}

}
