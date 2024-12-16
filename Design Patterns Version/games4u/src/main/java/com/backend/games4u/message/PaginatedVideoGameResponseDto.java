package com.backend.games4u.message;

import java.util.List;

public class PaginatedVideoGameResponseDto {

    private long totalItems;

    private long totalPages;

    private long currentPage;

    private List<VideoGameResponseDto> videoGames;

    public PaginatedVideoGameResponseDto(long totalItems, long totalPages, long currentPage, List<VideoGameResponseDto> videoGames) {
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.videoGames = videoGames;
    }

    public PaginatedVideoGameResponseDto() {

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

    public List<VideoGameResponseDto> getVideoGames() {
        return videoGames;
    }

    public void setVideoGames(List<VideoGameResponseDto> videoGames) {
        this.videoGames = videoGames;
    }
}
