import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NpsPrioritizedComponent } from './nps-prioritized.component';

describe('NpsPrioritizedComponent', () => {
  let component: NpsPrioritizedComponent;
  let fixture: ComponentFixture<NpsPrioritizedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NpsPrioritizedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NpsPrioritizedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
