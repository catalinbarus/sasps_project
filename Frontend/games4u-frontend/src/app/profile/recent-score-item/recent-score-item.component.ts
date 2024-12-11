import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { Image } from 'src/app/models/image.model';
import { Score } from 'src/app/models/score.model';
import { VideoGame } from 'src/app/models/video-game.model';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-recent-score-item',
  templateUrl: './recent-score-item.component.html',
  styleUrls: ['./recent-score-item.component.css']
})
export class RecentScoreItemComponent implements OnInit, OnDestroy {

  @Input()
  score: Score;

  @Input()
  userId: number;

  videoGame: VideoGame;

  constructor(
    private storageService: StorageService
  ) {}

  ngOnInit(): void {

    this.storageService.fetchVideoGameById(this.score.gameId)
    .subscribe({
      next: (data) => {
        this.videoGame = data;
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

}
