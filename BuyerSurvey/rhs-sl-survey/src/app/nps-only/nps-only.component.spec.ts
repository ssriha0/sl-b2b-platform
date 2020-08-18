import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NpsOnlyComponent } from './nps-only.component';

describe('NpsOnlyComponent', () => {
  let component: NpsOnlyComponent;
  let fixture: ComponentFixture<NpsOnlyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NpsOnlyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NpsOnlyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
