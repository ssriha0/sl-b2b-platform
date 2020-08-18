import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CsatOnlyComponent } from './csat-only.component';

describe('CsatOnlyComponent', () => {
  let component: CsatOnlyComponent;
  let fixture: ComponentFixture<CsatOnlyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CsatOnlyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CsatOnlyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
