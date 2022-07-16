import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import {
	UserAuthGuard,
	AnonymousUserGuard
} from './user.guards';

import { UserLoginComponent } from './component/user-login.component';
import { UserLogoutComponent } from './component/user-logout.component';

const routes: Routes = [
	{ path: '', redirectTo: 'login', pathMatch: 'full'},
	{ path: 'login', component: UserLoginComponent, canActivate: [AnonymousUserGuard] },
	{ path: 'logout', component: UserLogoutComponent, canActivate: [UserAuthGuard] },
];

@NgModule({
	imports: [RouterModule.forChild(routes)],
	exports: [RouterModule]
})
export class UserRoutingModule { }
