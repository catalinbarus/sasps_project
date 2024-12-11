package com.backend.games4u.message;

public class MyGameListEntryPostDto {

    private long videoGameId;

    private long userId;

    private long myGameListStatusId;

    public MyGameListEntryPostDto() {

    }

    public MyGameListEntryPostDto(long videoGameId, long userId, long myGameListStatusId) {
        this.videoGameId = videoGameId;
        this.userId = userId;
        this.myGameListStatusId = myGameListStatusId;
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
}
