import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { SheetModifyComponent } from './component/sheet-modify.component';
import { SheetCreateComponent } from './component/sheet-create.component';
import { SheetResultComponent } from './component/sheet-result.component';

const routes: Routes = [
	{ path: '', redirectTo: 'sheet/result', pathMatch: 'full'},
	{ path: 'sheet/create', component: SheetCreateComponent },
	{ path: 'sheet/result', component: SheetResultComponent },
	{ path: 'sheet/:sheet', component: SheetModifyComponent },
	{ path: 'sheet/:sheet/track/:track', component: SheetModifyComponent }
];

@NgModule({
	imports: [RouterModule.forChild(routes)],
	exports: [RouterModule]
})
export class MusicRoutingModule { }
