import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { Subscription } from 'rxjs';
import { PaginatedVideoGames } from 'src/app/models/paginated-video-games.model';
import { VideoGame } from 'src/app/models/video-game.model';
import { StorageService } from 'src/app/services/storage.service';
import { UserService } from 'src/app/services/user.service';
import { VideoGameService } from 'src/app/services/video-game.service';

@Component({
  selector: 'app-games-search',
  templateUrl: './games-search.component.html',
  styleUrls: ['./games-search.component.css']
})
export class GamesSearchComponent implements OnInit, OnDestroy {

  paginatedGames: PaginatedVideoGames;
  videoGames: VideoGame[] = [];
  subscription: Subscription = new Subscription;
  searchedGameSubscription: Subscription = new Subscription;
  detailedGameSubscription: Subscription = new Subscription;
  isLoggedIn: boolean = false;

  gameName: string;
  gamesSearched: boolean = false;

  page: number = 1;
  count: number = 0;
  pageSize: number = 5;

  videoGame: VideoGame;

  constructor(private userService: UserService, 
    private videoGameService: VideoGameService,
    private storageService: StorageService) { }

  ngOnInit(): void {

    this.gameName = this.videoGameService.getSearchedGame();
    this.searchedGameSubscription = this.videoGameService.searchedGameChanged.subscribe(
      (gameName: string) => {
        this.gameName = gameName;
      }
    );

    if (this.gameName) {
      this.gamesSearched = true;
    }

    this.retrieveGames();
    this.subscription = this.videoGameService.videoGamesChanged.subscribe(
      (videoGames: VideoGame[]) => {
        this.videoGames = videoGames;
      }
    );

    // this.storageService.fetchVideoGame(25).subscribe();
    // this.videoGame = this.videoGameService.getDetailedGame();
    // this.detailedGameSubscription = this.videoGameService.detailedVideoGameChanged.subscribe(
    //   (videoGame: VideoGame) => {
    //     this.videoGame = videoGame;
    //   }
    // );
  }

  ngOnDestroy(): void {
    //console.log(this.videoGame);
  }


  onSubmit() {
    this.page = 1;
    this.retrieveGames();
    this.gamesSearched = true;
  }

  getRequestParams(gameName: string, page: number, pageSize: number): any {
    let params: any = {};

    if (gameName) {
      params[`gameName`] = gameName;
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
    const params = this.getRequestParams(this.gameName, this.page, this.pageSize);

    this.storageService.fetchVideoGamesPaginated(params)
      .subscribe({
        next: (data) => {
          this.paginatedGames = data;
          
          this.videoGames = this.paginatedGames.videoGames;
          this.videoGameService.setVideoGames(this.videoGames);
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






}
