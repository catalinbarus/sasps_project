package com.backend.games4u.message;

public class ScorePutDto {

    private String score;

    private long gameId;

    public ScorePutDto() {

    }

    public ScorePutDto(String score, long gameId) {
        this.score = score;
        this.gameId = gameId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }
}
