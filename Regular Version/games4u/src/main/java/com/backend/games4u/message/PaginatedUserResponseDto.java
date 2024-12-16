package com.backend.games4u.message;

import java.util.List;

public class PaginatedUserResponseDto {

    private long totalItems;

    private long totalPages;

    private long currentPage;

    private List<UserResponseDto> users;

    public PaginatedUserResponseDto(long totalItems, long totalPages, long currentPage, List<UserResponseDto> users) {
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.users = users;
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

    public List<UserResponseDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserResponseDto> users) {
        this.users = users;
    }
}
