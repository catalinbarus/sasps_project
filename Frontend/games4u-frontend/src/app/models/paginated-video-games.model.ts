import { VideoGame } from "./video-game.model";

export class PaginatedVideoGames {

    public totalItems: number;

    public totalPages: number;

    public currentPage: number;

    public videoGames: VideoGame[];

    constructor(
        totalItems: number,
        totalPages: number,
        currentPage: number, 
        videoGames: VideoGame[]
    ) {
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.videoGames = videoGames;
    }
}