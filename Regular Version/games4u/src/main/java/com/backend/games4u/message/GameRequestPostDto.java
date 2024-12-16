package com.backend.games4u.message;

public class GameRequestPostDto {

    private String gameName;

    private String link;

    private String details;

    private String userId;

    public GameRequestPostDto(String gameName, String link, String details, String userId) {
        this.gameName = gameName;
        this.link = link;
        this.details = details;
        this.userId = userId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
