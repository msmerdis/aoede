import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserAuthGuard } from 'aoede-client-user';

const routes: Routes = [
	{ path: '', redirectTo: 'music', pathMatch: 'full'},
	{ path: 'music', loadChildren: () => import('aoede-client-music').then(m => m.MusicModule), canActivate: [UserAuthGuard] },
	{ path: 'user', loadChildren: () => import('aoede-client-user').then(m => m.UserModule) },
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule { }
