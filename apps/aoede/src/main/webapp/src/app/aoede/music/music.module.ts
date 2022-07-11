import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';

import { MusicRoutingModule } from './music-routing.module';
import { MusicService } from './music.service';
import { MusicCanvasService } from './music-canvas.service';
import { SheetComponent } from './component/sheet.component';
import { SheetCreateComponent } from './component/sheet-create.component';
import { SheetResultComponent } from './component/sheet-result.component';
import * as fromMusicState from './store/music.reducer';
import { MusicEffects } from './store/music.effects';
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
		StoreModule.forFeature(fromMusicState.musicFeatureKey, fromMusicState.musicReducer, {}),
		EffectsModule.forFeature([MusicEffects])
	],
	exports: [
		CommonModule,
		FormsModule,
		ReactiveFormsModule,
		HttpClientModule
	],
	providers: [
		MusicService,
		MusicCanvasService
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
