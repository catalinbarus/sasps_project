package com.backend.games4u.message;

public class MyGameListEntryPutDto {

    private long myGameListStatusId;

    public MyGameListEntryPutDto() {

    }

    public MyGameListEntryPutDto(long myGameListStatusId) {
        this.myGameListStatusId = myGameListStatusId;
    }

    public long getMyGameListStatusId() {
        return myGameListStatusId;
    }

    public void setMyGameListStatusId(long myGameListStatusId) {
        this.myGameListStatusId = myGameListStatusId;
    }
}
