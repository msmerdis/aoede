import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { SheetComponent } from './sheet/sheet.component';
import { SheetCreateComponent } from './sheet/sheet-create.component';
import { SheetSearchComponent } from './sheet/sheet-search.component';

const routes: Routes = [
	{ path: '', redirectTo: 'sheet/search', pathMatch: 'full'},
	{ path: 'sheet/create', component: SheetCreateComponent },
	{ path: 'sheet/search', component: SheetSearchComponent },
	{ path: 'sheet/:id', component: SheetComponent }
];

@NgModule({
	imports: [RouterModule.forChild(routes)],
	exports: [RouterModule]
})
export class MusicRoutingModule { }
