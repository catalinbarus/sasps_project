import { VideoGameLight } from "./video-game-light.model";

export class PaginatedVideoGamesLight {

    public totalItems: number;

    public totalPages: number;

    public currentPage: number;

    public videoGames: VideoGameLight[];

    constructor(
        totalItems: number,
        totalPages: number,
        currentPage: number, 
        videoGames: VideoGameLight[]
    ) {
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.videoGames = videoGames;
    }
}