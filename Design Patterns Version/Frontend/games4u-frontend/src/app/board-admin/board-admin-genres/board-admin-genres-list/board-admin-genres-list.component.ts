import { HttpClient } from '@angular/common/http';
import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Genre } from 'src/app/models/genre.model';
import { PaginatedGenres } from 'src/app/models/paginated-genres.model';
import { GenreService } from 'src/app/services/genre.service';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-board-admin-genres-list',
  templateUrl: './board-admin-genres-list.component.html',
  styleUrls: ['./board-admin-genres-list.component.css']
})
export class BoardAdminGenresListComponent implements OnInit, OnDestroy {

  genreName: string;
  paginatedGenres: PaginatedGenres;
  genres: Genre[];
  subscription: Subscription = new Subscription;
  newGenre: string;

  @Input()
  page: number;

  @Input()
  count: number;

  @Input()
  pageSize: number;

  constructor(
    private genreService: GenreService, 
    private storageService: StorageService,
    private http: HttpClient,
    private route: ActivatedRoute,
    private router: Router,) {}

  ngOnInit(): void {

    this.retrieveGenres();
    this.subscription = this.genreService.genresChanged.subscribe(
      (genres: Genre[]) => {
        this.genres = genres;
      }
    );
    
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  getRequestParams(genreName: string, page: number, pageSize: number): any {
    let params: any = {};

    if (genreName) {
      params[`genreName`] = genreName;
    }

    if (page) {
      params[`page`] = page - 1;
    }

    if (pageSize) {
      params[`size`] = pageSize;
    }

    return params;
  }

  retrieveGenres() {
    const params = this.getRequestParams(this.genreName, this.page, this.pageSize);

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

  onSubmit() {
    this.page = 1;
    this.retrieveGenres();
  }

  onGenreAdd() {

    let genreToAdd = {"genre" : this.newGenre};

    this.http.post<any>('http://localhost:8080/genres', genreToAdd).subscribe(
      {
        next: data => {
          console.log(data);
        },
        error: err => {
          console.log("Error while adding genre.")
        }
      }
    );

    this.genreService.changeGenresStatus();
    window.location.reload();
  }

  handlePageChange(event: number): void {
    this.page = event;
    this.retrieveGenres();
  }
}
