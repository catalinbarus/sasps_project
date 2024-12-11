package com.backend.games4u.message;

public class TrailerPostDto {

    private String youtubeId;

    private String gameId;

    public TrailerPostDto(String youtubeId, String gameId) {
        this.youtubeId = youtubeId;
        this.gameId = gameId;
    }

    public String getYoutubeId() {
        return youtubeId;
    }

    public void setYoutubeId(String youtubeId) {
        this.youtubeId = youtubeId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
