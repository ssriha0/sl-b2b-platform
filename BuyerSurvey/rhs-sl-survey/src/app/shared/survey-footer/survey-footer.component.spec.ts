import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SurveyFooterComponent } from './survey-footer.component';

describe('SurveyFooterComponent', () => {
  let component: SurveyFooterComponent;
  let fixture: ComponentFixture<SurveyFooterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SurveyFooterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SurveyFooterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
