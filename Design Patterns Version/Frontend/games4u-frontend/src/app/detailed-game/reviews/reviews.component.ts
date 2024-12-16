import { HttpClient } from '@angular/common/http';
import { Component, OnInit, OnDestroy, Renderer2 } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { PaginatedReviews } from 'src/app/models/paginated-reviews.model';
import { Review } from 'src/app/models/review.model';
import { VideoGame } from 'src/app/models/video-game.model';
import { StorageService } from 'src/app/services/storage.service';
import { VideoGameService } from 'src/app/services/video-game.service';
import { DetailedGameComponent } from '../detailed-game.component';

@Component({
  selector: 'app-reviews',
  templateUrl: './reviews.component.html',
  styleUrls: ['./reviews.component.css']
})
export class ReviewsComponent implements OnInit, OnDestroy {

  reviewForm: FormGroup;
  paginatedReviews: PaginatedReviews;
  videoGame: VideoGame;
  detailedGameSubscription: Subscription = new Subscription;
  gameReviews: Review[];
  page: number = 1;
  count: number = 0;
  pageSize: number = 3;
  reviewOrder = ["createdAt,desc", "createdAt,asc"];
  defaultReviewOrder = "createdAt,desc";
  currentReview = null;
  displayStyle = "none";
  user: any;
  isLoggedIn = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private videoGameService: VideoGameService,
    private storageService: StorageService,
    private renderer: Renderer2,
    private http: HttpClient,
    private toastr: ToastrService,
    private detailedGame: DetailedGameComponent
  ) {}

  ngOnInit(): void {
    this.detailedGame.selectedAction = this.detailedGame.actions[1];
    this.user = this.storageService.getUser();
    this.videoGame = this.videoGameService.getDetailedGame();
    this.isLoggedIn = this.storageService.isLoggedIn();
    this.retrieveReviews();

    this.storageService.fetchUserReviewForGame(this.user.id, this.videoGame.id)
      .subscribe({
        next: (data) => {
          this.currentReview = data;
        },
        error: (err) => {
          console.log(err);
        }
    });

    this.initForm();
  }

  initForm() {
    let reviewText = '';

    this.reviewForm = new FormGroup({
      review: new FormControl(reviewText, Validators.required)
    });
  }

  onSubmit() {
    console.log(this.reviewForm);
  }

  getRequestParams(sort: string, page: number, pageSize: number): any {
    let params: any = {};

    if (sort) {
      params[`sort`] = sort;
    }

    if (page) {
      params[`page`] = page - 1;
    }

    if (pageSize) {
      params[`size`] = pageSize;
    }

    return params;
  }

  retrieveReviews() {
    const params = this.getRequestParams(this.defaultReviewOrder, this.page, this.pageSize);

    this.storageService.fetchMostRecentReviews(params, this.videoGame.id)
      .subscribe({
        next: (data) => {
          console.log(data);
          this.paginatedReviews = data;
          
          this.gameReviews = this.paginatedReviews.reviews;
          this.count = this.paginatedReviews.totalItems;
        },
        error: (err) => {
          console.log(err);
        }
      });
  }

  ngOnDestroy(): void {
    console.log(this.currentReview);
  }

  formatSortToParam(param: string) {

    if (param == "Oldest") {
      return "createdAt,asc";
    }

    return "createdAt,desc";
  }

  formatSortToText(param: string) {

    if (param == "createdAt,asc") {
      return "Oldest";
    }

    return "Newest";
  }

  handlePageChange(event: number): void {
    this.page = event;
    this.retrieveReviews();
  }

  handleReviewOrder(event: any): void {
    this.defaultReviewOrder = this.formatSortToParam(event.target.value);
    this.page = 1;
    this.retrieveReviews();
  }

  openPopup() {

    if (this.isLoggedIn) {
      if (this.currentReview != null) {
        this.reviewForm.controls['review'].setValue(this.currentReview['review']);
      }
  
      this.displayStyle = "block";
      this.renderer.addClass(document.body, 'blur');
      setTimeout(() => {
        this.renderer.addClass(document.querySelector('.modal'), 'in');
      }, 50);
    } else {
      this.toastr.error('You need to login to write a review.', 'Unauthorized', {
        closeButton: true
      });
    }
  }
  
  closePopup() {
    this.displayStyle = "none";
    this.renderer.removeClass(document.querySelector('.modal'), 'in');
    setTimeout(() => {
      this.displayStyle = "none";
      this.renderer.removeClass(document.body, 'blur');
    }, 300);
  }

  postReview() {

    let reviewItem = {
      "review" : this.reviewForm.value['review'],
      "gameId" : this.videoGame.id,
      "userId" : this.user.id
    }

    this.http.post<any>('http://localhost:8080/reviews', reviewItem).subscribe(
      {
        next: data => {
          //this.gameSet = true;
          this.retrieveReviews();
        },
        error: err => {
          console.log(err);
        }
      }
    );

    this.closePopup();
  }

  putReview() {

    let updatedReview = {
      "review" : this.reviewForm.value['review']
    }

    this.http.put<any>('http://localhost:8080/reviews/' + this.currentReview!['id'], updatedReview).subscribe();

    this.closePopup();
  }
}
