import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SheetResultComponent } from './create.component';

describe('SheetResultComponent', () => {
	let component: SheetResultComponent;
	let fixture: ComponentFixture<SheetResultComponent>;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			declarations: [ SheetResultComponent ]
		})
		.compileComponents();
	});

	beforeEach(() => {
		fixture = TestBed.createComponent(SheetResultComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
