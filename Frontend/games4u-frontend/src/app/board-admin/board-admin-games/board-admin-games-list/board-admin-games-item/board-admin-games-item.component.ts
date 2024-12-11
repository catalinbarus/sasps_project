import { HttpClient } from '@angular/common/http';
import { Component, OnInit, Input, Renderer2 } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Image } from 'src/app/models/image.model';
import { VideoGame } from 'src/app/models/video-game.model';
import { VideoGameService } from 'src/app/services/video-game.service';

@Component({
  selector: 'app-board-admin-games-item',
  templateUrl: './board-admin-games-item.component.html',
  styleUrls: ['./board-admin-games-item.component.css']
})
export class BoardAdminGamesItemComponent implements OnInit {

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

  displayStyle = "none";
  
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

  onGameDelete() {
    this.http.delete('http://localhost:8080/games/' + this.videoGame.id)
        .subscribe({
            next: data => {
                this.status = 'Delete successful';
            },
            error: error => {
                this.errorMessage = error.message;
                console.error('There was an error!', error);
            }
        });
    this.videoGameService.deleteVideoGame(this.videoGame);
    this.closePopup();
  }

  editGame() {
    this.videoGameService.setEditableVideoGame(this.videoGame);
    this.videoGameService.changeEditGameStatus();
    this.router.navigate(['edit_video_game/' + this.videoGame.id], { relativeTo: this.route });
  }
}
