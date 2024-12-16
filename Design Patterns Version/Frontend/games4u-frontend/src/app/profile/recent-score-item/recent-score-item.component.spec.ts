import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecentScoreItemComponent } from './recent-score-item.component';

describe('RecentScoreItemComponent', () => {
  let component: RecentScoreItemComponent;
  let fixture: ComponentFixture<RecentScoreItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecentScoreItemComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecentScoreItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
