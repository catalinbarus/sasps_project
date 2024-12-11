import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { Subscription } from 'rxjs';
import { PaginatedVideoGames } from 'src/app/models/paginated-video-games.model';
import { VideoGame } from 'src/app/models/video-game.model';
import { StorageService } from 'src/app/services/storage.service';
import { UserService } from 'src/app/services/user.service';
import { VideoGameService } from 'src/app/services/video-game.service';

@Component({
  selector: 'app-board-admin-games-list',
  templateUrl: './board-admin-games-list.component.html',
  styleUrls: ['./board-admin-games-list.component.css']
})
export class BoardAdminGamesListComponent implements OnInit, OnDestroy {

  paginatedGames: PaginatedVideoGames;
  videoGames: VideoGame[] = [];
  subscription: Subscription = new Subscription;
  isLoggedIn: boolean = false;
  gameName: string;

  @Input()
  page: number;

  @Input()
  count: number;

  @Input()
  pageSize: number;

  constructor(private userService: UserService, 
    private videoGameService: VideoGameService,
    private storageService: StorageService) { }

  ngOnInit(): void {

    this.retrieveGames();
    this.subscription = this.videoGameService.videoGamesChanged.subscribe(
      (videoGames: VideoGame[]) => {
        this.videoGames = videoGames;
      }
    );
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  onSubmit() {
    this.page = 1;
    this.retrieveGames();
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

  // onGameClick(game:any) {
  //   console.log(game['videoGame']);
  // }
}
