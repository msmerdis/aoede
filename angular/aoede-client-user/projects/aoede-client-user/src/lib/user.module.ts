import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import { HTTP_INTERCEPTORS } from '@angular/common/http';

import { UserRoutingModule } from './user-routing.module';
import { UserService } from './user.service';
import { UserControlService } from './user-control.service';
import { UserLoginComponent } from './component/user-login.component';
import * as fromUserState from './store/user.reducer';
import { UserEffects } from './store/user.effects';
import { UserInterceptor } from './user.interceptor';
import {
	UserAuthGuard,
	AnonymousUserGuard
} from './user.guards';
import {
	UserConfig,
	UserConfigToken,
	DefaultUserConfig
} from './user.config';

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
		UserControlService,
		UserAuthGuard,
		AnonymousUserGuard,
		{ provide: HTTP_INTERCEPTORS, useClass: UserInterceptor, multi: true }
	]
})
export class UserModule {
	static forRoot(config: UserConfig): ModuleWithProviders<UserModule> {
		return {
			ngModule: UserModule,
			providers: [
				{
					provide  : UserConfigToken,
					useValue : {...DefaultUserConfig, ...config}
				}
			]
		}
	}
}

