import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MusicRoutingModule } from './music-routing.module';
import { SheetComponent } from './sheet/sheet.component';
import { SheetCreateComponent } from './sheet/sheet-create.component';
import { StoreModule } from '@ngrx/store';
import * as fromMusicState from './reducers';

@NgModule({
	declarations: [
		SheetComponent,
		SheetCreateComponent
	],
	imports: [
		MusicRoutingModule,
		CommonModule,
		FormsModule,
		ReactiveFormsModule,
		StoreModule.forFeature(fromMusicState.musicStateFeatureKey, fromMusicState.reducers, { metaReducers: fromMusicState.metaReducers })
	],
	exports: [
		CommonModule,
		FormsModule,
		ReactiveFormsModule
	]
})
export class MusicModule { }
