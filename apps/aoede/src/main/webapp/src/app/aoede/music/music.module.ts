import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MusicRoutingModule } from './music-routing.module';
import { SheetComponent } from './sheet/sheet.component';
import { SheetCreateComponent } from './sheet/sheet-create.component';

@NgModule({
	declarations: [
		SheetComponent,
		SheetCreateComponent
	],
	imports: [
		MusicRoutingModule,
		CommonModule
	],
	exports: [
		CommonModule
	]
})
export class MusicModule { }
