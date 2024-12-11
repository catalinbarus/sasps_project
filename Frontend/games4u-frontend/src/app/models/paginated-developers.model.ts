import { GameDeveloper } from "./game-developer.model";

export class PaginatedDevelopers {

    public totalItems: number;

    public totalPages: number;

    public currentPage: number;

    public entities: GameDeveloper[];

    constructor(
        totalItems: number,
        totalPages: number,
        currentPage: number, 
        entities: GameDeveloper[]
    ) {
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.entities = entities;
    }
}