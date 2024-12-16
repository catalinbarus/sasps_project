import { Component, OnInit, OnDestroy, Renderer2 } from '@angular/core';
import { StorageService } from '../services/storage.service';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { User } from '../models/user.model';
import { MyGameListEntry } from '../models/my-game-list-entry.model';
import { PaginatedMyGameListEntry } from '../models/paginated-my-game-list-entry.model';
import { Score } from '../models/score.model';
import { PaginatedReviews } from '../models/paginated-reviews.model';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit, OnDestroy {
  currentUser: any;
  userId: number;

  isLoggedUserOnTheirProfile: boolean = false;

  userOfProfile: User;
  meanScore: string;

  currentlyPlayingEntries: MyGameListEntry[];
  finishedEntries: MyGameListEntry[];
  droppedEntries: MyGameListEntry[];
  planToPlayEntries: MyGameListEntry[];

  currentlyPlayingGames: number;
  finishedGames: number;
  droppedGames: number;
  planToPlayGames: number;

  currentlyPlayingPixels: number = 0;
  finishedPixels: number = 0;
  droppedPixels: number = 0;
  planToPlayPixels: number = 0;

  page = 1;
  count = 0;
  pageSize = 3;
  paginatedGameUpdates: PaginatedMyGameListEntry;
  gameUpdates: MyGameListEntry[];

  recentScores: Score[];
  recentReviews: PaginatedReviews;

  displayStyle = "none";
  displayStyleAboutPage = "none";


  constructor(
    private storageService: StorageService,
    private route: ActivatedRoute,
    private router: Router,
    private renderer: Renderer2) { }

  ngOnInit(): void {
    this.currentUser = this.storageService.getUser();

    this.route.params.subscribe((params: Params) => {
      this.userId = +params['id'];
    });

    this.storageService.fetchUserById(this.userId).subscribe(
      {
        next: (data) => {
          this.userOfProfile = data;

          if (this.currentUser.id == this.userOfProfile.id) {
            this.isLoggedUserOnTheirProfile = true;
          }
        },
        error: (err) => {
          console.log(err);
        }
      }
    );

    this.storageService.fetchMeanScoreForUser(this.userId).subscribe(
      {
        next: (data) => {
          this.meanScore = data;
        },
        error: (err) => {
          console.log(err);
        }
      }
    );

    this.storageService.fetchGameListEntriesForUser(this.userId, 1)
      .subscribe({
        next: (data) => {
          this.currentlyPlayingEntries = data;
          this.currentlyPlayingGames = this.currentlyPlayingEntries.length;
        },
        error: (err) => {
          console.log(err);
        }
    });

    this.storageService.fetchGameListEntriesForUser(this.userId, 2)
      .subscribe({
        next: (data) => {
          this.finishedEntries = data;
          this.finishedGames = this.finishedEntries.length;
        },
        error: (err) => {
          console.log(err);
        }
    });

    this.storageService.fetchGameListEntriesForUser(this.userId, 3)
      .subscribe({
        next: (data) => {
          this.droppedEntries = data;
          this.droppedGames = this.droppedEntries.length;
        },
        error: (err) => {
          console.log(err);
        }
    });

    this.storageService.fetchGameListEntriesForUser(this.userId, 4)
      .subscribe({
        next: (data) => {
          this.planToPlayEntries = data;
          this.planToPlayGames = this.planToPlayEntries.length;
        },
        error: (err) => {
          console.log(err);
        }
    });

    this.storageService.fetchRecentScoresForUser(this.userId)
      .subscribe({
        next: (data) => {
          this.recentScores = data;
        },
        error: (err) => {
          console.log(err);
        }
    });

    this.storageService.fetchRecentReviewsForUser(this.userId)
      .subscribe({
        next: (data) => {
          this.recentReviews = data;
          
        },
        error: (err) => {
          console.log(err);
        }
      });

    //this.calculateSectionWidths(this.currentlyPlayingGames, this.finishedGames, this.droppedGames, this.planToPlayGames);

    setTimeout(() => {
      this.calculateSectionWidths(this.currentlyPlayingGames, this.finishedGames, this.droppedGames, this.planToPlayGames);
    }, 200);

    this.retrieveGameUpdates();
  }

  ngOnDestroy(): void {
    //console.log(this.recentReviews);
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

  retrieveGameUpdates() {
    let params;

    params = this.getRequestParams(this.page, this.pageSize);

    this.storageService.fetchRecentGameUpdatesForUser(params, this.userId)
      .subscribe({
        next: (data) => {
          this.paginatedGameUpdates = data;
          this.gameUpdates = this.paginatedGameUpdates.entries;
          this.count = this.paginatedGameUpdates.totalItems;
        },
        error: (err) => {
          console.log(err);
        }
      });
  }

  formatPicture(convertedData: string): string {
    return 'data:image/png;base64,' + convertedData;
  }

  calculateSectionWidths(playingEntries: number, finishedEntries: number, droppedEntries: number, planToPlayEntries: number): void {
    const totalEntries: number = playingEntries + finishedEntries + droppedEntries + planToPlayEntries;
    const chartWidth: number = 380;

    if (totalEntries === 0) {
        return;
    }

    let playingWidth: number = Math.floor((playingEntries * chartWidth) / totalEntries);
    let finishedWidth: number = Math.floor((finishedEntries * chartWidth) / totalEntries);
    let droppedWidth: number = Math.floor((droppedEntries * chartWidth) / totalEntries);
    let planToPlayWidth: number = Math.floor((planToPlayEntries * chartWidth) / totalEntries);

    if (playingWidth > 0) {
        console.log(`Playing: ${playingWidth}px`);
        this.currentlyPlayingPixels = playingWidth;
    }
    if (finishedWidth > 0) {
        console.log(`Finished: ${finishedWidth}px`);
        this.finishedPixels = finishedWidth;
    }
    if (droppedWidth > 0) {
        console.log(`Dropped: ${droppedWidth}px`);
        this.droppedPixels = droppedWidth;
    }
    if (planToPlayWidth > 0) {
        console.log(`Plan to Play: ${planToPlayWidth}px`);
        this.planToPlayPixels = planToPlayWidth;
    }
  }

  openPopup() {
    this.displayStyle = "block";
    this.renderer.addClass(document.body, 'blur');
    setTimeout(() => {
      this.renderer.addClass(document.querySelector('.modal'), 'in');
    }, 50);
  }
  
  closePopup() {
    this.displayStyle = "none";
    this.renderer.removeClass(document.querySelector('.modal'), 'in');
    setTimeout(() => {
      this.displayStyle = "none";
      this.renderer.removeClass(document.body, 'blur');
    }, 300);
  }

  openPopupAboutPage() {
    this.displayStyleAboutPage = "block";
    this.renderer.addClass(document.body, 'blur');
    setTimeout(() => {
      this.renderer.addClass(document.querySelector('.modal'), 'in');
    }, 50);
  }
  
  closePopupAboutPage() {
    this.displayStyleAboutPage = "none";
    this.renderer.removeClass(document.querySelector('.modal'), 'in');
    setTimeout(() => {
      this.displayStyle = "none";
      this.renderer.removeClass(document.body, 'blur');
    }, 300);
  }

  formatNumber(meanScoreString: string) {
    return parseFloat(meanScoreString).toFixed(2);
  }
}