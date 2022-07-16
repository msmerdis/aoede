import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SheetModifyComponent } from './sheet-modify.component';

describe('SheetModifyComponent', () => {
	let component: SheetModifyComponent;
	let fixture: ComponentFixture<SheetModifyComponent>;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			declarations: [ SheetModifyComponent ]
		})
		.compileComponents();
	});

	beforeEach(() => {
		fixture = TestBed.createComponent(SheetModifyComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
