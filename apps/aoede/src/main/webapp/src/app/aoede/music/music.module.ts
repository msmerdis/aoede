import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { StoreModule } from '@ngrx/store';

import { MusicRoutingModule } from './music-routing.module';
import { SheetComponent } from './sheet/sheet.component';
import { SheetCreateComponent } from './sheet/sheet-create.component';
import { SheetResultComponent } from './sheet/sheet-result.component';
import * as fromMusicState from './store/music.reducer';
import {
	MusicConfig,
	MusicConfigToken,
	DefaultMusicConfig
} from './music.config';

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
export class MusicModule {
	static forRoot(config: MusicConfig): ModuleWithProviders<MusicModule> {
		return {
			ngModule: MusicModule,
			providers: [
				{
					provide  : MusicConfigToken,
					useValue : {...DefaultMusicConfig, ...config}
				}
			]
		}
	}
}
