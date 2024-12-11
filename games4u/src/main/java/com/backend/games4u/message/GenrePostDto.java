package com.backend.games4u.message;

public class GenrePostDto {

    private String genre;

    public GenrePostDto() {
    }

    public GenrePostDto(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
