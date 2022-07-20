import { TestBed } from '@angular/core/testing';

import { FractionService } from './fraction.service';

describe('FractionService', () => {
	let service: FractionService;

	beforeEach(() => {
		TestBed.configureTestingModule({});
		service = TestBed.inject(FractionService);
	});

	it('should be created', () => {
		expect(service).toBeTruthy();
	});
});
