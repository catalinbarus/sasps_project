import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit, Renderer2 } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Image } from 'src/app/models/image.model';
import { VideoGame } from 'src/app/models/video-game.model';
import { VideoGameService } from 'src/app/services/video-game.service';

@Component({
  selector: 'app-board-admin-images-item',
  templateUrl: './board-admin-images-item.component.html',
  styleUrls: ['./board-admin-images-item.component.css']
})
export class BoardAdminImagesItemComponent implements OnInit {

  @Input()
  videoGame: VideoGame;

  status: string;

  errorMessage: string;

  showButtons: boolean;

  constructor(
    private http: HttpClient, 
    private renderer: Renderer2,
    private videoGameService: VideoGameService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.showButtons = false;
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

  editGameImages() {
    this.videoGameService.setVideoGameImages(this.videoGame.images);
    this.videoGameService.changeVideoGameImagesStatus();
    this.router.navigate([this.videoGame.id], { relativeTo: this.route });
  }

}
