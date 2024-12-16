import { Component, OnInit, OnDestroy, Input, Renderer2 } from '@angular/core';
import { Image } from 'src/app/models/image.model';
import { Review } from 'src/app/models/review.model';
import { VideoGame } from 'src/app/models/video-game.model';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-recent-review-item',
  templateUrl: './recent-review-item.component.html',
  styleUrls: ['./recent-review-item.component.css']
})
export class RecentReviewItemComponent implements OnInit, OnDestroy {

  @Input()
  recentReview: Review;

  videoGame: VideoGame;

  displayStyle = "none";

  constructor(
    private storageService: StorageService,
    private renderer: Renderer2
  ) {}

  ngOnInit(): void {
    this.storageService.fetchVideoGameById(this.recentReview.game_id)
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

}
