import { GamePublisher } from "./game-publisher.model";

export class PaginatedPublishers {

    public totalItems: number;

    public totalPages: number;

    public currentPage: number;

    public entities: GamePublisher[];

    constructor(
        totalItems: number,
        totalPages: number,
        currentPage: number, 
        entities: GamePublisher[]
    ) {
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.entities = entities;
    }
}