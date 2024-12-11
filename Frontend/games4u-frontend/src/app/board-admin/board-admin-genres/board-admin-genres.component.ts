import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Genre } from 'src/app/models/genre.model';
import { PaginatedGenres } from 'src/app/models/paginated-genres.model';
import { GenreService } from 'src/app/services/genre.service';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-board-admin-genres',
  templateUrl: './board-admin-genres.component.html',
  styleUrls: ['./board-admin-genres.component.css']
})
export class BoardAdminGenresComponent implements OnInit {

  paginatedGenres: PaginatedGenres;
  genres: Genre[];
  subscription: Subscription = new Subscription;
  page: number = 1;
  count: number = 0;
  pageSize: number = 6;

  constructor(
    private storageService: StorageService,
    private genreService: GenreService
  ) {}

  ngOnInit(): void {

    this.retrieveGenres();
    this.subscription = this.genreService.genresChanged.subscribe(
      (genres: Genre[]) => {
        this.genres = genres;
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

  retrieveGenres() {
    const params = this.getRequestParams(this.page, this.pageSize);

    this.storageService.fetchGenresPaginated(params)
      .subscribe({
        next: (data) => {
          console.log(data);
          this.paginatedGenres = data;
          
          this.genres = this.paginatedGenres.genres;
          this.genreService.setGenres(this.genres);
          this.count = this.paginatedGenres.totalItems;
        },
        error: (err) => {
          console.log(err);
        }
      });
  }

}
