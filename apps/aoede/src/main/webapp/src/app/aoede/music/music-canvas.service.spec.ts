import { TestBed } from '@angular/core/testing';

import { MusicCanvasService } from './music-canvas.service';

describe('MusicCanvasService', () => {
	let service: MusicCanvasService;

	beforeEach(() => {
		TestBed.configureTestingModule({});
		service = TestBed.inject(MusicCanvasService);
	});

	it('should be created', () => {
		expect(service).toBeTruthy();
	});
});
