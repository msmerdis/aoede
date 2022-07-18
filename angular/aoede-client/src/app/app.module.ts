import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';
import { HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { StringifyPipe } from './common/stringify.pipe';

import { UserModule } from 'aoede-client-user';
import { MusicModule } from 'aoede-client-music';
import * as fromAppState from './store/app.reducer';
import { AppEffects } from './store/app.effects';
import { environment } from '../environments/environment';
import { ApiInterceptor } from './interceptors/api.interceptor';

@NgModule({
	declarations: [
		AppComponent,
		FooterComponent,
		HeaderComponent
	],
	imports: [
		BrowserModule,
		AppRoutingModule,
		UserModule.forRoot({}),
		MusicModule.forRoot({}),
		StoreModule.forRoot(fromAppState.reducer, {}),
		EffectsModule.forRoot(
			!environment.production ? [AppEffects] : []
		),
		!environment.production ? StoreDevtoolsModule.instrument() : []
	],
	providers: [
		{ provide: HTTP_INTERCEPTORS, useClass: ApiInterceptor, multi: true }
	],
	bootstrap: [AppComponent]
})
export class AppModule { }
