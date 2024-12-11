import { User } from "./user.model";

export class PaginatedUsers {

    public totalItems: number;

    public totalPages: number;

    public currentPage: number;

    public users: User[];

    constructor(
        totalItems: number,
        totalPages: number,
        currentPage: number, 
        users: User[]
    ) {
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.users = users;
    }
}