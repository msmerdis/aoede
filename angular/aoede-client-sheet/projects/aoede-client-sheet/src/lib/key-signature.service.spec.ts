import { TestBed } from '@angular/core/testing';

import { KeySignatureService } from './key-signature.service';

describe('KeySignatureService', () => {
	let service: KeySignatureService;

	beforeEach(() => {
		TestBed.configureTestingModule({});
		service = TestBed.inject(KeySignatureService);
	});

	it('should be created', () => {
		expect(service).toBeTruthy();
	});
});
