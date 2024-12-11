import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecentReviewItemComponent } from './recent-review-item.component';

describe('RecentReviewItemComponent', () => {
  let component: RecentReviewItemComponent;
  let fixture: ComponentFixture<RecentReviewItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecentReviewItemComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecentReviewItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
