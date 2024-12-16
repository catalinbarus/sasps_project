package com.backend.games4u.message;

import java.util.List;

public class PaginatedMyGameListEntryResponseDto {

    private long totalItems;

    private long totalPages;

    private long currentPage;

    private List<MyGameListEntryResponseDto> entries;

    public PaginatedMyGameListEntryResponseDto(long totalItems, long totalPages, long currentPage, List<MyGameListEntryResponseDto> entries) {
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.entries = entries;
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

    public List<MyGameListEntryResponseDto> getEntries() {
        return entries;
    }

    public void setEntries(List<MyGameListEntryResponseDto> entries) {
        this.entries = entries;
    }
}
