import { Component, OnInit, OnDestroy } from '@angular/core';
import { UserService } from '../services/user.service';
import { GenreService } from '../services/genre.service';
import { Genre } from '../models/genre.model';
import { StorageService } from '../services/storage.service';
import { Subscription } from 'rxjs';
import { VideoGameService } from '../services/video-game.service';
import { VideoGame } from '../models/video-game.model';
import { PaginatedVideoGames } from '../models/paginated-video-games.model';
import { Image } from '../models/image.model';
import { ActivatedRoute, Router } from '@angular/router';
import { VideoGameLight } from '../models/video-game-light.model';
import { PaginatedVideoGamesLight } from '../models/paginated-video-games-light.model';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy {
  content?: string;
  genres: Genre[] = [];
  videoGames: VideoGameLight[] = [];
  subscription: Subscription = new Subscription;
  recommendedGames: VideoGame[];

  paginatedGames: PaginatedVideoGamesLight;
  isLoggedIn: boolean = false;
  user: any;
  page = 1;
  count = 0;
  pageSize = 6;
  gameName: string;
  recommendationCount = 3;
  recommendationLength = 0;

  constructor(
    private userService: UserService, 
    private genreService: GenreService,
    private videoGameService: VideoGameService,
    private storageService: StorageService,
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit(): void {

    this.isLoggedIn = this.storageService.isLoggedIn();
    this.user = this.storageService.getUser();

    this.userService.getPublicContent().subscribe({
      next: data => {
        this.content = data;
      },
      error: err => {console.log(err)
        if (err.error) {
          this.content = JSON.parse(err.error).message;
        } else {
          this.content = "Error with status: " + err.status;
        }
      }
    });

    this.retrieveGames();

    if (this.isLoggedIn) {
      this.retrieveRecommendations();
    }
  }

  ngOnDestroy(): void {
    console.log(this.recommendedGames);
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

  getRecommendationsParams(pageSize: number): any {
    let params: any = {};

    if (pageSize) {
      params[`size`] = pageSize;
    }

    return params;
  }

  retrieveGames() {
    const params = this.getRequestParams(this.page, this.pageSize);

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

  retrieveRecommendations() {
    const params = this.getRecommendationsParams(this.recommendationCount);

    this.storageService.fetchGameRecommendationsForUser(params, this.user.id)
      .subscribe({
        next: (data) => {
          this.recommendedGames = data;
          this.recommendationLength = this.recommendedGames.length;
          
        },
        error: (err) => {
          console.log(err);
        }
      });
  }

  onGameSearch() {
    this.videoGameService.setSearchedGame(this.gameName);
    this.videoGameService.changeSearchedGameStatus();
    this.router.navigate(['games']);
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
}