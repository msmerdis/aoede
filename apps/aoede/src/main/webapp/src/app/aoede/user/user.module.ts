import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { StoreModule } from '@ngrx/store';

import { UserRoutingModule } from './user-routing.module';
import { UserLoginComponent } from './user-login.component';
import { UserService } from './user.service';
import * as fromUserState from './user.reducer';

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
		StoreModule.forFeature(fromUserState.userFeatureKey, fromUserState.userReducer, {})
	],
	exports: [
		CommonModule,
		FormsModule,
		ReactiveFormsModule,
		HttpClientModule
	],
	providers: [
		UserService
	]
})
export class UserModule { }
