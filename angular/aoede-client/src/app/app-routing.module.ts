import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
	{ path: '', redirectTo: 'music', pathMatch: 'full'},
	{ path: 'music', loadChildren: () => import('aoede-client-music').then(m => m.MusicModule) },
	{ path: 'user', loadChildren: () => import('aoede-client-user').then(m => m.UserModule) },
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule { }
