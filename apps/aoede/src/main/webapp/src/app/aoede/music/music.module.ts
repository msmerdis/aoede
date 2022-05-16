import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { StoreModule } from '@ngrx/store';

import { MusicRoutingModule } from './music-routing.module';
import { SheetComponent } from './sheet/sheet.component';
import { SheetCreateComponent } from './sheet/sheet-create.component';
import { SheetResultComponent } from './sheet/sheet-result.component';
import * as fromMusicState from './store/music.reducer';

@NgModule({
	declarations: [
		SheetComponent,
		SheetCreateComponent,
		SheetResultComponent
	],
	imports: [
		MusicRoutingModule,
		CommonModule,
		FormsModule,
		ReactiveFormsModule,
		HttpClientModule,
		StoreModule.forFeature(fromMusicState.musicFeatureKey, fromMusicState.musicReducer, {})
	],
	exports: [
		CommonModule,
		FormsModule,
		ReactiveFormsModule,
		HttpClientModule
	]
})
export class MusicModule { }
