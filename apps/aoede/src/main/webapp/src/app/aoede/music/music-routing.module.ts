import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { SheetComponent } from './sheet/sheet.component';
import { SheetCreateComponent } from './sheet/sheet-create.component';
import { SheetResultComponent } from './sheet/sheet-result.component';

const routes: Routes = [
	{ path: '', redirectTo: 'sheet/search', pathMatch: 'full'},
	{ path: 'sheet/create', component: SheetCreateComponent },
	{ path: 'sheet/result', component: SheetResultComponent },
	{ path: 'sheet/:id', component: SheetComponent }
];

@NgModule({
	imports: [RouterModule.forChild(routes)],
	exports: [RouterModule]
})
export class MusicRoutingModule { }
