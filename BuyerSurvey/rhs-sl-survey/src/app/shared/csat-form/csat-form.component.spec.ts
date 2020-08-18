import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CsatFormComponent } from './csat-form.component';

describe('CsatFormComponent', () => {
  let component: CsatFormComponent;
  let fixture: ComponentFixture<CsatFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CsatFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CsatFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
