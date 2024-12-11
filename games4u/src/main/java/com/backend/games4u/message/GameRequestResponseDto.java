package com.backend.games4u.message;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class GameRequestResponseDto {

    private long id;
    private String gameName;

    private String link;

    private String details;

    private String userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date createdAt;

    public GameRequestResponseDto(long id, String gameName, String link, String details, String userId, Date createdAt) {
        this.id = id;
        this.gameName = gameName;
        this.link = link;
        this.details = details;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
