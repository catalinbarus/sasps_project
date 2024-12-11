package com.backend.games4u.message;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class MyGameListEntryResponseDto {

    private long id;

    private long videoGameId;

    private long userId;

    private long myGameListStatusId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;

    public MyGameListEntryResponseDto(long id, long videoGameId, long userId, long myGameListStatusId, Date timestamp) {
        this.id = id;
        this.videoGameId = videoGameId;
        this.userId = userId;
        this.myGameListStatusId = myGameListStatusId;
        this.timestamp = timestamp;
    }

    public MyGameListEntryResponseDto() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVideoGameId() {
        return videoGameId;
    }

    public void setVideoGameId(long videoGameId) {
        this.videoGameId = videoGameId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getMyGameListStatusId() {
        return myGameListStatusId;
    }

    public void setMyGameListStatusId(long myGameListStatusId) {
        this.myGameListStatusId = myGameListStatusId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
