import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlanToPlayListComponent } from './plan-to-play-list.component';

describe('PlanToPlayListComponent', () => {
  let component: PlanToPlayListComponent;
  let fixture: ComponentFixture<PlanToPlayListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PlanToPlayListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlanToPlayListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
