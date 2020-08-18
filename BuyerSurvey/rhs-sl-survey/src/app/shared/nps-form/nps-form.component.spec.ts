import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NpsFormComponent } from './nps-form.component';

describe('NpsFormComponent', () => {
  let component: NpsFormComponent;
  let fixture: ComponentFixture<NpsFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NpsFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NpsFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
