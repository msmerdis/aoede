import { NgModule, ModuleWithProviders } from '@angular/core';
import {
	SheetConfig,
	SheetConfigToken,
	DefaultSheetConfig
} from './sheet.config';
import { SheetComponent } from './component/sheet.component';

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
