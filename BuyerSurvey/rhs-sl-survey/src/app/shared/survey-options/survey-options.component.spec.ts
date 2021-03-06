import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SurveyOptionsComponent } from './survey-options.component';

describe('SurveyOptionsComponent', () => {
  let component: SurveyOptionsComponent;
  let fixture: ComponentFixture<SurveyOptionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SurveyOptionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SurveyOptionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
