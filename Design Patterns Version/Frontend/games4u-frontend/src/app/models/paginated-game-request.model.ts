import { GameRequest } from "./game-request.model";

export class PaginatedGameRequests {

    public totalItems: number;

    public totalPages: number;

    public currentPage: number;

    public gameRequests: GameRequest[];

    constructor(
        totalItems: number,
        totalPages: number,
        currentPage: number, 
        gameRequests: GameRequest[]) {
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.gameRequests = gameRequests;
    }
}