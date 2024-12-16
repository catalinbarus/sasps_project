package com.backend.games4u.message;

public class TrailerResponseDto {

    private long id;

    private String youtubeId;

    private long gameId;

    public TrailerResponseDto(long id, String youtubeId, long gameId) {
        this.id = id;
        this.youtubeId = youtubeId;
        this.gameId = gameId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getYoutubeId() {
        return youtubeId;
    }

    public void setYoutubeId(String youtubeId) {
        this.youtubeId = youtubeId;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }
}
