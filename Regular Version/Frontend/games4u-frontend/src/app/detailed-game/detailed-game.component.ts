import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { VideoGame } from '../models/video-game.model';
import { Subscription } from 'rxjs';
import { VideoGameService } from '../services/video-game.service';
import { StorageService } from '../services/storage.service';
import { Image } from '../models/image.model';
import { GameDetailActionItem } from '../models/game-detail-action-item.model';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { MyGameListEntry } from '../models/my-game-list-entry.model';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-detailed-game',
  templateUrl: './detailed-game.component.html',
  styleUrls: ['./detailed-game.component.css']
})
export class DetailedGameComponent implements OnInit, OnDestroy {

  gameId: number;
  videoGame: VideoGame;
  detailedGameSubscription: Subscription = new Subscription;
  givenScore: number = 0;
  scoreId: number;
  user: any;
  isLoggedIn = false;
  gameSet: boolean = false;

  myGameListEntry: MyGameListEntry;
  isGameInList: boolean = false;
  givenStatus: number = 0;

  actions: GameDetailActionItem[] = [
    {
      name: "Details",
      routeParam: "details"
    },
    {
      name: "Reviews",
      routeParam: "reviews"
    },
    {
      name: "Trailers",
      routeParam: "trailers"
    },
    {
      name: "Pictures",
      routeParam: "pictures"
    }
  ]

  scores = [
    "Select",
    "(10) Masterpiece",
    "(9) Great",
    "(8) Very Good",
    "(7) Good",
    "(6) Fine",
    "(5) Average",
    "(4) Bad",
    "(3) Very Bad",
    "(2) Horrible",
    "(1) Appalling"
  ]

  selectedAction: GameDetailActionItem = this.actions[0];
  coverPictures: Image[];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private videoGameService: VideoGameService,
    private storageService: StorageService,
    private http: HttpClient,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {

    this.user = this.storageService.getUser();
    this.isLoggedIn = this.storageService.isLoggedIn();

    this.route.params.subscribe((params: Params) => {
      this.gameId = +params['id'];
    });

    this.storageService.fetchVideoGame(this.gameId).subscribe();
    this.videoGame = this.videoGameService.getDetailedGame();
    this.detailedGameSubscription = this.videoGameService.detailedVideoGameChanged.subscribe(
      (videoGame: VideoGame) => {
        this.videoGame = videoGame;
        this.coverPictures = this.videoGame.images.filter((image) => {
          return image.name.slice(0, 6) != "boxart";
        });

        this.storageService.fetchUserScoreForGame(this.user.id, this.videoGame.id)
        .subscribe({
          next: (data) => {
            this.givenScore = data.score;
            this.scoreId = data.id;
            if (this.givenScore > 0) {
              this.gameSet = true;
            }
          },
          error: (err) => {
            console.log(err);
          }
        });

        this.storageService.fetchGameListEntryOfUser(this.user.id, this.videoGame.id)
        .subscribe({
          next: (data) => {
            this.myGameListEntry = data;

            if (this.myGameListEntry.id != 0) {
              this.isGameInList = true;
              this.givenStatus = this.myGameListEntry.myGameListStatusId;
            }
          },
          error: (err) => {
            console.log(err);
          }
        });
      }
    );
  }

  ngOnDestroy(): void {
    console.log(this.isGameInList);
  }

  grade() {
    console.log(this.givenScore);
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

  itemSelected(action: GameDetailActionItem) {
    this.selectedAction = action;
  }

  formatScoreReception(averageScore: string) {
    if (averageScore.slice(0, 1) == "1") {
      if (averageScore.slice(0, 2) == "10") {
        return "Masterpiece";
      }

      return "Appaling";
    } else if (averageScore.slice(0, 1) == "2") {
      return "Horrible";
    } else if (averageScore.slice(0, 1) == "3") {
      return "Very Bad";
    } else if (averageScore.slice(0, 1) == "4") {
      return "Bad";
    } else if (averageScore.slice(0, 1) == "5") {
      return "Average";
    } else if (averageScore.slice(0, 1) == "6") {
      return "Fine";
    } else if (averageScore.slice(0, 1) == "7") {
      return "Good";
    } else if (averageScore.slice(0, 1) == "8") {
      return "Very Good";
    } else if (averageScore.slice(0, 1) == "0") {
      return "";
    }

    return "Great";
  }

  updateGameScore() {

    let score = {
      "score" : this.givenScore,
      "gameId" : this.videoGame.id
    }

    this.http.put<any>('http://localhost:8080/scores/' + this.scoreId, score).subscribe();

    this.toastr.success('Updated score for game', 'Success', {
      closeButton: true
    });
  }

  setGameScore() {

    if (this.isLoggedIn) {
      let score = {
        "score" : this.givenScore,
        "gameId" : this.videoGame.id,
        "userId" : this.user.id
      }
  
      this.http.post<any>('http://localhost:8080/scores', score).subscribe(
        {
          next: data => {
            this.gameSet = true;
          },
          error: err => {
            console.log(err);
          }
        }
      );
  
      this.toastr.success('Game was graded.', 'Success', {
        closeButton: true
      });
    } else {
      this.toastr.error('You need to login to grade a game.', 'Unauthorized', {
        closeButton: true
      });
    }
  }

  updateGameStatus() {

    let updatedStatus = {
      "myGameListStatusId" : this.givenStatus
    }

    this.http.put<any>('http://localhost:8080/game_list_entries/' + this.myGameListEntry.id, updatedStatus).subscribe();

    this.toastr.success('Updated status for game', 'Success', {
      closeButton: true
    });
  }

  setGameStatus() {

    if (this.isLoggedIn) {
      let newStatus = {
        "videoGameId" : this.videoGame.id,
        "userId" : this.user.id,
        "myGameListStatusId" : this.givenStatus
      }
  
      this.http.post<any>('http://localhost:8080/game_list_entries', newStatus).subscribe(
        {
          next: data => {
            this.isGameInList = true;
          },
          error: err => {
            console.log(err);
          }
        }
      );
  
      this.toastr.success('Game was added to list.', 'Success', {
        closeButton: true
      });
    } else {
      this.toastr.error('You need to login to have a personalized list.', 'Unauthorized', {
        closeButton: true
      });
    }
  }

  jumpToTop() {
    window.scrollTo(0, 1);
  }
}
