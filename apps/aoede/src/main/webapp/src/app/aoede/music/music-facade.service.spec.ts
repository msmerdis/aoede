import { TestBed } from '@angular/core/testing';

import { MusicFacadeService } from './music-facade.service';

describe('MusicFacadeService', () => {
	let service: MusicFacadeService;

	beforeEach(() => {
		TestBed.configureTestingModule({});
		service = TestBed.inject(MusicFacadeService);
	});

	it('should be created', () => {
		expect(service).toBeTruthy();
	});
});
