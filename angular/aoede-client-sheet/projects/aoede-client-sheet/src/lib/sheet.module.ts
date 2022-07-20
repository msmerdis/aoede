import { NgModule, ModuleWithProviders } from '@angular/core';
import {
	SheetConfig,
	SheetConfigToken,
	DefaultSheetConfig
} from './sheet.config';
import { SheetComponent } from './component/sheet.component';

import { MeasureService } from './service/measure.service';
import { StaveService } from './service/stave.service';
import { ClefService } from './service/clef.service';
import { BeatService } from './service/beat.service';
import { SheetService } from './service/sheet.service';
import { NoteService } from './service/note.service';
import { FractionService } from './service/fraction.service';
import { TempoService } from './service/tempo.service';
import { KeySignatureService } from './service/key-signature.service';


@NgModule({
	declarations: [
		SheetComponent
	],
	imports: [],
	exports: [
		SheetComponent
	],
	providers: []

})
export class SheetModule {
	static forRoot(config: SheetConfig): ModuleWithProviders<SheetModule> {
		return {
			ngModule: SheetModule,
			providers: [
				{
					provide  : SheetConfigToken,
					useValue : {...DefaultSheetConfig, ...config}
				}
			]
		}
	}
}
