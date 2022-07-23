import { TestBed } from '@angular/core/testing';

import { MusicControlService } from './music-control.service';

describe('MusicControlService', () => {
	let service: MusicControlService;

	beforeEach(() => {
		TestBed.configureTestingModule({});
		service = TestBed.inject(MusicControlService);
	});

	it('should be created', () => {
		expect(service).toBeTruthy();
	});
});
