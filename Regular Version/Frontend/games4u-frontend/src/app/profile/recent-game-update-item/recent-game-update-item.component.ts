import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { Image } from 'src/app/models/image.model';
import { MyGameListEntry } from 'src/app/models/my-game-list-entry.model';
import { VideoGame } from 'src/app/models/video-game.model';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-recent-game-update-item',
  templateUrl: './recent-game-update-item.component.html',
  styleUrls: ['./recent-game-update-item.component.css']
})
export class RecentGameUpdateItemComponent implements OnInit, OnDestroy {

  @Input()
  myGameListEntry: MyGameListEntry;

  @Input()
  userId: number;

  videoGame: VideoGame;
  gameScore: number;

  constructor(
    private storageService: StorageService
  ) {}

  ngOnInit(): void {

    this.storageService.fetchVideoGameById(this.myGameListEntry.videoGameId)
    .subscribe({
      next: (data) => {
        this.videoGame = data;
      },
      error: (err) => {
        console.log(err);
      }
    });

    this.storageService.fetchUserScoreForGame(this.userId, this.myGameListEntry.videoGameId)
    .subscribe({
      next: (data) => {
        console.log(data);
        this.gameScore = data.score;
      },
      error: (err) => {
        console.log(err);
      }
    });
  }

  ngOnDestroy(): void {
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

  formatGameStatus(statusId: number): string {

    if (statusId == 1) {
      return "Currently Playing";
    } else if (statusId == 2) {
      return "Finished";
    } else if (statusId == 3) {
      return "Dropped";
    }

    return "Plan to Play";
  }
}
