import { HttpClient } from '@angular/common/http';
import { Component, OnInit, OnDestroy, Renderer2 } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Image } from 'src/app/models/image.model';
import { VideoGameService } from 'src/app/services/video-game.service';

@Component({
  selector: 'app-edit-images',
  templateUrl: './edit-images.component.html',
  styleUrls: ['./edit-images.component.css']
})
export class EditImagesComponent implements OnInit, OnDestroy {

  images: Image[];
  subscription: Subscription = new Subscription;
  showDetails: boolean;
  displayStyle = "none";
  selectedImage: Image;

  constructor(
    private videoGameService: VideoGameService,
    private http: HttpClient,
    private renderer: Renderer2,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.images = this.videoGameService.getVideoGameImages();
    this.subscription = this.videoGameService.videoGameImagesChanged.subscribe(
      (images: Image[]) => {
        this.images = images;
      }
    );
  }

  ngOnDestroy(): void {
    console.log(this.images);
  }

  formatPicture(convertedData: string): string {
    return 'data:image/png;base64,' + convertedData;
  }

  openPopup(image: Image) {
    this.selectedImage = image;

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

  downloadPicture(url: string) {
    window.open(url, '_blank');
  }

  onGameImageDelete(image: Image) {
    this.http.delete('http://localhost:8080/files/' + image.id).subscribe();
    this.videoGameService.deleteVideoGameImage(image);
    this.videoGameService.changeVideoGameImagesStatus();
    this.closePopup();
  }

  onCancel() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }

}
