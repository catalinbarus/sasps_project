import { HttpClient } from '@angular/common/http';
import { Component, OnInit, Renderer2 } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { Trailer } from 'src/app/models/trailer.model';
import { VideoGameService } from 'src/app/services/video-game.service';

@Component({
  selector: 'app-edit-trailers',
  templateUrl: './edit-trailers.component.html',
  styleUrls: ['./edit-trailers.component.css']
})
export class EditTrailersComponent implements OnInit {

  trailers: Trailer[];
  subscription: Subscription = new Subscription;
  showDetails: boolean;
  displayStyle = "none";
  selectedTrailer: Trailer;

  constructor(
    private videoGameService: VideoGameService,
    private http: HttpClient,
    private renderer: Renderer2,
    private route: ActivatedRoute,
    private router: Router,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.trailers = this.videoGameService.getVideoGameTrailers();
    this.subscription = this.videoGameService.videoGameTrailersChanged.subscribe(
      (trailers: Trailer[]) => {
        this.trailers = trailers;
      }
    );
  }

  openPopup(trailer: Trailer) {
    this.selectedTrailer = trailer;

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

  onGameTrailerDelete(trailer: Trailer) {
    this.http.delete('http://localhost:8080/trailers/' + trailer.id).subscribe();
    this.closePopup();

    this.router.navigate(['../'], { relativeTo: this.route });

    this.toastr.success('Trailer was deleted.', 'Success', {
      closeButton: true
    });
  }

  onCancel() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }

}
