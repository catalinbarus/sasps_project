import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { PaginatedVideoGames } from 'src/app/models/paginated-video-games.model';
import { VideoGame } from 'src/app/models/video-game.model';
import { StorageService } from 'src/app/services/storage.service';
import { VideoGameService } from 'src/app/services/video-game.service';

@Component({
  selector: 'app-board-admin-images',
  templateUrl: './board-admin-images.component.html',
  styleUrls: ['./board-admin-images.component.css']
})
export class BoardAdminImagesComponent implements OnInit {

  paginatedGames: PaginatedVideoGames;
  videoGames: VideoGame[] = [];
  subscription: Subscription = new Subscription;
  page: number = 1;
  count: number = 0;
  pageSize: number = 5;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private videoGameService: VideoGameService,
    private storageService: StorageService
  ) { }

  ngOnInit(): void {
    this.retrieveGames();
    this.subscription = this.videoGameService.videoGamesChanged.subscribe(
      (videoGames: VideoGame[]) => {
        this.videoGames = videoGames;
      }
    );
  }


  getRequestParams(page: number, pageSize: number): any {
    let params: any = {};

    if (page) {
      params[`page`] = page - 1;
    }

    if (pageSize) {
      params[`size`] = pageSize;
    }

    return params;
  }

  retrieveGames() {
    const params = this.getRequestParams(this.page, this.pageSize);

    this.storageService.fetchVideoGamesPaginated(params)
      .subscribe({
        next: (data) => {
          this.paginatedGames = data;
          
          this.videoGames = this.paginatedGames.videoGames;
          this.videoGameService.setVideoGames(this.videoGames);
          this.count = this.paginatedGames.totalItems;
        },
        error: (err) => {
          console.log(err);
        }
      });
  }
}
