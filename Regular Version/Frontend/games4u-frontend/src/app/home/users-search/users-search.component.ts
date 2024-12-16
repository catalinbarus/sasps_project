import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { PaginatedUsers } from 'src/app/models/paginated-users.model';
import { PaginatedVideoGames } from 'src/app/models/paginated-video-games.model';
import { User } from 'src/app/models/user.model';
import { VideoGame } from 'src/app/models/video-game.model';
import { StorageService } from 'src/app/services/storage.service';
import { UserService } from 'src/app/services/user.service';
import { VideoGameService } from 'src/app/services/video-game.service';

@Component({
  selector: 'app-users-search',
  templateUrl: './users-search.component.html',
  styleUrls: ['./users-search.component.css']
})
export class UsersSearchComponent implements OnInit, OnDestroy {

  paginatedUsers: PaginatedUsers;
  users: User[] = [];
  subscription: Subscription = new Subscription;
  searchedUserSubscription: Subscription = new Subscription;
  detailedGameSubscription: Subscription = new Subscription;
  isLoggedIn: boolean = false;

  userName: string;
  usersSearched: boolean = false;

  page: number = 1;
  count: number = 0;
  pageSize: number = 5;

  videoGame: VideoGame;

  constructor(private userService: UserService, 
    private videoGameService: VideoGameService,
    private storageService: StorageService) { }

  ngOnInit(): void {

    //this.gameName = this.videoGameService.getSearchedGame();
    // this.searchedUserSubscription = this.videoGameService.searchedGameChanged.subscribe(
    //   (gameName: string) => {
    //     this.gameName = gameName;
    //   }
    // );

    if (this.userName) {
      this.usersSearched = true;
    }

    this.retrieveUsers();
    this.subscription = this.userService.usersChanged.subscribe(
      (users: User[]) => {
        this.users = users;
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
    this.retrieveUsers();
    this.usersSearched = true;
  }

  getRequestParams(userName: string, page: number, pageSize: number): any {
    let params: any = {};

    if (userName) {
      params[`userName`] = userName;
    }

    if (page) {
      params[`page`] = page - 1;
    }

    if (pageSize) {
      params[`size`] = pageSize;
    }

    return params;
  }

  retrieveUsers() {
    const params = this.getRequestParams(this.userName, this.page, this.pageSize);

    this.storageService.fetchUsersPaginated(params)
      .subscribe({
        next: (data) => {
          console.log(data);
          this.paginatedUsers = data;
          
          this.users = this.paginatedUsers.users;
          this.userService.setUsers(this.users);
          this.count = this.paginatedUsers.totalItems;
        },
        error: (err) => {
          console.log(err);
        }
      });
  }

  handlePageChange(event: number): void {
    this.page = event;
    this.retrieveUsers();
  }
}
