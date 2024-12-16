import { MyGameListEntry } from "./my-game-list-entry.model";

export class PaginatedMyGameListEntry {

    public totalItems: number;

    public totalPages: number;

    public currentPage: number;

    public entries: MyGameListEntry[];

    constructor(
        totalItems: number,
        totalPages: number,
        currentPage: number, 
        entries: MyGameListEntry[]
    ) {
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.entries = entries;
    }
}