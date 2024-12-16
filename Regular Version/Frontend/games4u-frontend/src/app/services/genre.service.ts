import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { Genre } from '../models/genre.model';

@Injectable({
    providedIn: 'root',
  })
export class GenreService {

    genresChanged = new Subject<Genre[]>();

    constructor() {}

    private genres: Genre[] = [];

    setGenres(genres: Genre[]) {
        this.genres = genres;
        this.genresChanged.next(this.genres.slice());
    }

    getGenres() {
        // Return a copy of the array, not a reference to the actual array
        return this.genres.slice();
    }

    changeGenresStatus() {
        this.genresChanged.next(this.genres.slice());
    }

    deleteGenre(genre: Genre) {
        const deleteIdx = this.genres.indexOf(genre);

        this.genres.splice(deleteIdx, 1);
        this.genresChanged.next(this.genres.slice());
    }

}