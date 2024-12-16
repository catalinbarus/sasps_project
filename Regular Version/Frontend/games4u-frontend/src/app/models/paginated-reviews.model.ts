import { Review } from "./review.model";

export class PaginatedReviews {

    public totalItems: number;

    public totalPages: number;

    public currentPage: number;

    public reviews: Review[];

    constructor(
        totalItems: number,
        totalPages: number,
        currentPage: number, 
        reviews: Review[]
    ) {
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.reviews = reviews;
    }
}