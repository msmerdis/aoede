import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserLoginComponent } from './create.component';

describe('UserLoginComponent', () => {
	let component: UserLoginComponent;
	let fixture: ComponentFixture<UserLoginComponent>;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			declarations: [ UserLoginComponent ]
		})
		.compileComponents();
	});

	beforeEach(() => {
		fixture = TestBed.createComponent(UserLoginComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
