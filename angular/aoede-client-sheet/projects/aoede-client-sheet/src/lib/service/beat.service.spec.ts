import { TestBed } from '@angular/core/testing';

import { BeatService } from './beat.service';

describe('BeatService', () => {
	let service: BeatService;

	beforeEach(() => {
		TestBed.configureTestingModule({});
		service = TestBed.inject(BeatService);
	});

	it('should be created', () => {
		expect(service).toBeTruthy();
	});
});
