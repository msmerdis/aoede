import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
	{ path: '', redirectTo: 'music', pathMatch: 'full'},
	{ path: 'music', loadChildren: () => import('./aoede/music/music.module').then(m => m.MusicModule) },
	{ path: 'user', loadChildren: () => import('./aoede/user/user.module').then(m => m.UserModule) },
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule { }
