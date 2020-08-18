import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SurveyOptionComponent } from './survey-option.component';

describe('SurveyOptionComponent', () => {
  let component: SurveyOptionComponent;
  let fixture: ComponentFixture<SurveyOptionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SurveyOptionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SurveyOptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
