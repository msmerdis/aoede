import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';

import { UserRoutingModule } from './user-routing.module';
import { UserService } from './user.service';
import { UserLoginComponent } from './component/user-login.component';
import * as fromUserState from './store/user.reducer';
import { UserEffects } from './store/user.effects';
import {
	UserAuthGuard,
	AnonymousUserGuard
} from './user.guards';

@NgModule({
	declarations: [
		UserLoginComponent
	],
	imports: [
		UserRoutingModule,
		CommonModule,
		FormsModule,
		ReactiveFormsModule,
		HttpClientModule,
		StoreModule.forFeature(fromUserState.userFeatureKey, fromUserState.reducer, {}),
		EffectsModule.forFeature([UserEffects])
	],
	exports: [
		CommonModule,
		FormsModule,
		ReactiveFormsModule,
		HttpClientModule
	],
	providers: [
		UserService,
		UserAuthGuard,
		AnonymousUserGuard
	]
})
export class UserModule { }
