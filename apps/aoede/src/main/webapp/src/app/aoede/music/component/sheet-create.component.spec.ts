import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SheetCreateComponent } from './create.component';

describe('SheetCreateComponent', () => {
	let component: SheetCreateComponent;
	let fixture: ComponentFixture<SheetCreateComponent>;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			declarations: [ SheetCreateComponent ]
		})
		.compileComponents();
	});

	beforeEach(() => {
		fixture = TestBed.createComponent(SheetCreateComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
