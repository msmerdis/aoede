import { TestBed } from '@angular/core/testing';

import { StaveSignatureService } from './stave-signature.service';

describe('StaveSignatureService', () => {
	let service: StaveSignatureService;

	beforeEach(() => {
		TestBed.configureTestingModule({});
		service = TestBed.inject(StaveSignatureService);
	});

	it('should be created', () => {
		expect(service).toBeTruthy();
	});
});
