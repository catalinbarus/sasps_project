import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { Review } from '../models/review.model';

@Injectable({
    providedIn: 'root',
  })
export class ReviewService {

    currentReviewChanged = new Subject<Review>();

    constructor() {}

    private currentReview: Review;

    setCurrentReview(review: Review) {
        this.currentReview = review;
        this.currentReviewChanged.next(this.currentReview);
    }

    getCurrentReview() {
        // Return a copy of the array, not a reference to the actual array
        return this.currentReview;
    }

    changeGenresStatus() {
        this.currentReviewChanged.next(this.currentReview);
    }
}