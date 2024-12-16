package com.backend.games4u.message;

public class GameDeveloperOrPublisherPostDto {

    private String name;

    private String establishDate;

    public GameDeveloperOrPublisherPostDto(String name, String establishDate) {
        this.name = name;
        this.establishDate = establishDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEstablishDate() {
        return establishDate;
    }

    public void setEstablishDate(String establishDate) {
        this.establishDate = establishDate;
    }
}
