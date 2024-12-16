import { Genre } from "./genre.model";

export class PaginatedGenres {

    public totalItems: number;

    public totalPages: number;

    public currentPage: number;

    public genres: Genre[];

    constructor(
        totalItems: number,
        totalPages: number,
        currentPage: number, 
        genres: Genre[]
    ) {
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.genres = genres;
    }
}