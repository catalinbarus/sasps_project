import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailsReviewItemComponent } from './details-review-item.component';

describe('DetailsReviewItemComponent', () => {
  let component: DetailsReviewItemComponent;
  let fixture: ComponentFixture<DetailsReviewItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DetailsReviewItemComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetailsReviewItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
