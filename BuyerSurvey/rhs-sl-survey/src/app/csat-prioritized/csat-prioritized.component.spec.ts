import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CsatPrioritizedComponent } from './csat-prioritized.component';

describe('CsatPrioritizedComponent', () => {
  let component: CsatPrioritizedComponent;
  let fixture: ComponentFixture<CsatPrioritizedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CsatPrioritizedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CsatPrioritizedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
