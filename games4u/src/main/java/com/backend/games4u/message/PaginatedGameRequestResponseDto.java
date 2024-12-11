package com.backend.games4u.message;

import java.util.List;

public class PaginatedGameRequestResponseDto {

    private long totalItems;

    private long totalPages;

    private long currentPage;

    private List<GameRequestResponseDto> gameRequests;

    public PaginatedGameRequestResponseDto(long totalItems, long totalPages, long currentPage, List<GameRequestResponseDto> gameRequests) {
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.gameRequests = gameRequests;
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

    public List<GameRequestResponseDto> getGameRequests() {
        return gameRequests;
    }

    public void setGameRequests(List<GameRequestResponseDto> gameRequests) {
        this.gameRequests = gameRequests;
    }
}
