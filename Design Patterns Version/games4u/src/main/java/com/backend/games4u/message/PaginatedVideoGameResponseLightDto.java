package com.backend.games4u.message;

import java.util.List;

public class PaginatedVideoGameResponseLightDto {
    private long totalItems;

    private long totalPages;

    private long currentPage;

    private List<VideoGameResponseLightDto> videoGames;

    public PaginatedVideoGameResponseLightDto(long totalItems, long totalPages, long currentPage, List<VideoGameResponseLightDto> videoGames) {
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.videoGames = videoGames;
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

    public List<VideoGameResponseLightDto> getVideoGames() {
        return videoGames;
    }

    public void setVideoGames(List<VideoGameResponseLightDto> videoGames) {
        this.videoGames = videoGames;
    }
}
