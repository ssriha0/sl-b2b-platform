import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NpsRatingComponent } from './nps-rating.component';

describe('NpsRatingComponent', () => {
  let component: NpsRatingComponent;
  let fixture: ComponentFixture<NpsRatingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NpsRatingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NpsRatingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
