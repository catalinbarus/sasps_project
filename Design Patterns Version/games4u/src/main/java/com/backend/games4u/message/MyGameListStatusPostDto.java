package com.backend.games4u.message;

public class MyGameListStatusPostDto {

    private String status;

    public MyGameListStatusPostDto(String status) {
        this.status = status;
    }

    public MyGameListStatusPostDto() {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
