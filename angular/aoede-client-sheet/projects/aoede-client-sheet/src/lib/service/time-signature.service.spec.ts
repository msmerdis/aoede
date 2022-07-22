import { TestBed } from '@angular/core/testing';

import { TimeSignatureService } from './time-signature.service';

describe('TimeSignatureService', () => {
	let service: TimeSignatureService;

	beforeEach(() => {
		TestBed.configureTestingModule({});
		service = TestBed.inject(TimeSignatureService);
	});

	it('should be created', () => {
		expect(service).toBeTruthy();
	});
});
