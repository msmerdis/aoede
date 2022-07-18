import { TestBed } from '@angular/core/testing';

import { ClefService } from './clef.service';

describe('ClefService', () => {
	let service: ClefService;

	beforeEach(() => {
		TestBed.configureTestingModule({});
	service = TestBed.inject(ClefService);
	});

	it('should be created', () => {
		expect(service).toBeTruthy();
	});
});
