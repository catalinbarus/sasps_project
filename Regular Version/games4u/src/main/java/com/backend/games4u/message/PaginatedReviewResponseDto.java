package com.backend.games4u.message;

import java.util.List;

public class PaginatedReviewResponseDto {

    private long totalItems;

    private long totalPages;

    private long currentPage;

    private List<ReviewResponseDto> reviews;

    public PaginatedReviewResponseDto(long totalItems, long totalPages, long currentPage, List<ReviewResponseDto> reviews) {
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.reviews = reviews;
    }

    public PaginatedReviewResponseDto() {

    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }

    public List<ReviewResponseDto> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewResponseDto> reviews) {
        this.reviews = reviews;
    }
}
