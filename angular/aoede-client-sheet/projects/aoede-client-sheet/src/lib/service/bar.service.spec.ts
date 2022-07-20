import { TestBed } from '@angular/core/testing';

import { BarService } from './bar.service';

describe('BarService', () => {
	let service: BarService;

	beforeEach(() => {
		TestBed.configureTestingModule({});
		service = TestBed.inject(BarService);
	});

	it('should be created', () => {
		expect(service).toBeTruthy();
	});
});
