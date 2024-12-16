package com.backend.games4u.message;

import java.util.List;

public class PaginatedGenreResponseDto {

    private long totalItems;

    private long totalPages;

    private long currentPage;

    private List<GenreResponseDto> genres;

    public PaginatedGenreResponseDto(long totalItems, long totalPages, long currentPage, List<GenreResponseDto> genres) {
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.genres = genres;
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

    public List<GenreResponseDto> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreResponseDto> genres) {
        this.genres = genres;
    }
}
