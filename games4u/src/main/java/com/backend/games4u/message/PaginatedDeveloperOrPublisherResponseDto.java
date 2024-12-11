package com.backend.games4u.message;

import java.util.List;

public class PaginatedDeveloperOrPublisherResponseDto {

    private long totalItems;

    private long totalPages;

    private long currentPage;

    private List<GameDeveloperOrPublisherResponseDto> entities;

    public PaginatedDeveloperOrPublisherResponseDto(long totalItems, long totalPages, long currentPage, List<GameDeveloperOrPublisherResponseDto> entities) {
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.entities = entities;
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

    public List<GameDeveloperOrPublisherResponseDto> getEntities() {
        return entities;
    }

    public void setEntities(List<GameDeveloperOrPublisherResponseDto> entities) {
        this.entities = entities;
    }
}
