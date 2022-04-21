import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';

import { UserModule } from './aoede/user/user.module';
import { MusicModule } from './aoede/music/music.module';
import { StoreModule } from '@ngrx/store';
import { reducers, metaReducers } from './reducers';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';
import { environment } from '../environments/environment';

@NgModule({
	declarations: [
		AppComponent,
		FooterComponent,
		HeaderComponent
	],
	imports: [
		BrowserModule,
		AppRoutingModule,
		UserModule,
		MusicModule,
		StoreModule.forRoot(reducers, { metaReducers }),
		!environment.production ? StoreDevtoolsModule.instrument() : []
	],
	providers: [],
	bootstrap: [AppComponent]
})
export class AppModule { }
