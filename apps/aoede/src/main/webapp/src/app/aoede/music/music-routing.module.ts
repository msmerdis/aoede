import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { SheetComponent } from './sheet/sheet.component';
import { SheetCreateComponent } from './sheet/sheet-create.component';

const routes: Routes = [
	{ path: '', redirectTo: 'sheet/create', pathMatch: 'full'},
	{ path: 'sheet/create', component: SheetCreateComponent },
	{ path: 'sheet/:id', component: SheetComponent }
];

@NgModule({
	imports: [RouterModule.forChild(routes)],
	exports: [RouterModule]
})
export class MusicRoutingModule { }
