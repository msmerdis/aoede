import { TestBed } from '@angular/core/testing';

import { AoedeClientSheetService } from './aoede-client-sheet.service';

describe('AoedeClientSheetService', () => {
  let service: AoedeClientSheetService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AoedeClientSheetService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
