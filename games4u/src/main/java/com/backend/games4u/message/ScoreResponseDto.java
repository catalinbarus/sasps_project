package com.backend.games4u.message;

public class ScoreResponseDto {

    private long id;

    private long score;

    private long userId;

    private long gameId;

    public ScoreResponseDto(long id, long score, long userId, long gameId) {
        this.id = id;
        this.score = score;
        this.userId = userId;
        this.gameId = gameId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    @Override
    public String toString() {
        return "ScoreResponseDto{" +
                "id=" + id +
                ", score=" + score +
                ", userId=" + userId +
                ", gameId=" + gameId +
                '}';
    }
}
