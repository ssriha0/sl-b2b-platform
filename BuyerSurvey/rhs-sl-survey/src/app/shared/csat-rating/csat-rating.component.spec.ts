import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CsatRatingComponent } from './csat-rating.component';

describe('CsatRatingComponent', () => {
  let component: CsatRatingComponent;
  let fixture: ComponentFixture<CsatRatingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CsatRatingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CsatRatingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
