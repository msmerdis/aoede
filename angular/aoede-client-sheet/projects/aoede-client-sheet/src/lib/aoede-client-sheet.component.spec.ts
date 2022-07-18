import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AoedeClientSheetComponent } from './aoede-client-sheet.component';

describe('AoedeClientSheetComponent', () => {
  let component: AoedeClientSheetComponent;
  let fixture: ComponentFixture<AoedeClientSheetComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AoedeClientSheetComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AoedeClientSheetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
