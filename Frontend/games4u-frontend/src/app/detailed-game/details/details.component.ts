import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Image } from 'src/app/models/image.model';
import { PaginatedReviews } from 'src/app/models/paginated-reviews.model';
import { Review } from 'src/app/models/review.model';
import { VideoGame } from 'src/app/models/video-game.model';
import { StorageService } from 'src/app/services/storage.service';
import { VideoGameService } from 'src/app/services/video-game.service';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit, OnDestroy {

  paginatedReviews: PaginatedReviews;
  videoGame: VideoGame;
  detailedGameSubscription: Subscription = new Subscription;
  gameReviews: Review[];
  page: number = 1;
  count: number = 0;
  pageSize: number = 3;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private videoGameService: VideoGameService,
    private storageService: StorageService
  ) {}

  ngOnInit(): void {
    this.videoGame = this.videoGameService.getDetailedGame();
    this.retrieveReviews();
  }

  getRequestParams(page: number, pageSize: number): any {
    let params: any = {};

    if (page) {
      params[`page`] = page - 1;
    }

    if (pageSize) {
      params[`size`] = pageSize;
    }

    return params;
  }

  retrieveReviews() {
    const params = this.getRequestParams(this.page, this.pageSize);

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
    console.log(this.gameReviews);
  }





}
