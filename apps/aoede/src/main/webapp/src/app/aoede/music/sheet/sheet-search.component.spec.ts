import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SheetSearchComponent } from './create.component';

describe('SheetSearchComponent', () => {
	let component: SheetSearchComponent;
	let fixture: ComponentFixture<SheetSearchComponent>;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			declarations: [ SheetSearchComponent ]
		})
		.compileComponents();
	});

	beforeEach(() => {
		fixture = TestBed.createComponent(SheetSearchComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
