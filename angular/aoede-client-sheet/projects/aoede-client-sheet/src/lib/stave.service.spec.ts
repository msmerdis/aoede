import { TestBed } from '@angular/core/testing';

import { StaveService } from './stave.service';

describe('StaveService', () => {
	let service: StaveService;

	beforeEach(() => {
		TestBed.configureTestingModule({});
		service = TestBed.inject(StaveService);
	});

	it('should be created', () => {
		expect(service).toBeTruthy();
	});
});
