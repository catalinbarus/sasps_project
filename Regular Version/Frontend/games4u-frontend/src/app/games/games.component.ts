import { Component, OnInit, OnDestroy } from '@angular/core';
import { VideoGame } from '../models/video-game.model';
import { Subscription } from 'rxjs';
import { StorageService } from '../services/storage.service';
import { UserService } from '../services/user.service';
import { VideoGameService } from '../services/video-game.service';
import { Image } from '../models/image.model';
import { PaginatedVideoGames } from '../models/paginated-video-games.model';
import { User } from '../models/user.model';
import { MyGameListEntry } from '../models/my-game-list-entry.model';
import { PaginatedVideoGamesLight } from '../models/paginated-video-games-light.model';
import { VideoGameLight } from '../models/video-game-light.model';

@Component({
  selector: 'app-games',
  templateUrl: './games.component.html',
  styleUrls: ['./games.component.css']
})
export class GamesComponent implements OnInit, OnDestroy {

  paginatedGames: PaginatedVideoGamesLight;
  videoGames: VideoGameLight[] = [];
  subscription: Subscription = new Subscription;
  isLoggedIn: boolean = false;
  page = 1;
  count = 0;
  pageSize = 20;
  user: any;
  detailedUser: User;
  myGameListEntries: MyGameListEntry[];

  constructor(private userService: UserService, 
    private videoGameService: VideoGameService,
    private storageService: StorageService) { }

  ngOnInit(): void {

    this.isLoggedIn = this.storageService.isLoggedIn();
    this.user = this.storageService.getUser();

    this.storageService.fetchUserById(this.user.id).subscribe(
      {
        next: (data) => {
          this.myGameListEntries = data.myGameListEntries;
        },
        error: (err) => {
          console.log(err);
        }
      }
    );

    this.retrieveGames();
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
    console.log(this.myGameListEntries);
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

  getRequestParamsLoggedIn(userId: number, page: number, pageSize: number): any {
    let params: any = {};

    if (userId) {
      params[`user_id`] = userId;
    }

    if (page) {
      params[`page`] = page - 1;
    }

    if (pageSize) {
      params[`size`] = pageSize;
    }

    return params;
  }

  retrieveGames() {
    let params;

    if (this.isLoggedIn) {
      params = this.getRequestParamsLoggedIn(this.user.id, this.page, this.pageSize);
    } else {
      params = this.getRequestParams(this.page, this.pageSize);
    }

    this.storageService.fetchVideoGamesPaginatedLight(params)
      .subscribe({
        next: (data) => {
          console.log(data);
          this.paginatedGames = data;
          
          this.videoGames = this.paginatedGames.videoGames;
          this.videoGameService.setVideoGamesLight(this.videoGames);
          this.count = this.paginatedGames.totalItems;
        },
        error: (err) => {
          console.log(err);
        }
      });
  }

  handlePageChange(event: number): void {
    this.page = event;
    this.retrieveGames();
  }

  formatPicture(convertedData: string): string {
    return 'data:image/png;base64,' + convertedData;
  }

  findBoxArtData(images: Image[]): string {

    for (let image of images) {
      if (image.name.slice(0, 6) == "boxart") {
        return image.convertedData;
      }
    }
    return "not found";
  }

  boxArtExists(images: Image[]): boolean {
    for (let image of images) {
      if (image.name.slice(0, 6) == "boxart") {
        return true;
      }
    }
    return false;
  }

  getPersonalScore(videoGame: VideoGameLight) {
    if (videoGame.scores.length > 0) {
      return videoGame.scores[0].score;
    }

    return "N/A";
  }

  getPersonalGameStatus(videoGame: VideoGameLight) {

    let finalStatus: string = "Not in your list"
    for (let entry of this.myGameListEntries) {

      if (videoGame.id == entry.videoGameId) {

        if (entry.myGameListStatusId == 1) {
          finalStatus = "Currently Playing";
          break;
        } else if (entry.myGameListStatusId == 2) {
          finalStatus = "Finished";
          break;
        } else if (entry.myGameListStatusId == 3) {
          finalStatus = "Dropped";
          break;
        } else if (entry.myGameListStatusId == 4) {
          finalStatus = "Plan to Play";
          break;
        }
      }
    }

    return finalStatus;
  }

}
