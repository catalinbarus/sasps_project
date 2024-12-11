import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { Image } from 'src/app/models/image.model';
import { MyGameListConfig } from 'src/app/models/my-game-list-config.model';
import { MyGameListEntry } from 'src/app/models/my-game-list-entry.model';
import { VideoGame } from 'src/app/models/video-game.model';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'tr[app-all-game-list-item]',
  templateUrl: './all-game-list-item.component.html',
  styleUrls: ['./all-game-list-item.component.css']
})
export class AllGameListItemComponent implements OnInit, OnDestroy {

  @Input()
  myGameListConfig: MyGameListConfig;

  @Input()
  myGameListEntry: MyGameListEntry;

  @Input()
  gameIndex: number;

  videoGame: VideoGame;

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
    
  }

  ngOnDestroy(): void {
    console.log(this.videoGame);
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
