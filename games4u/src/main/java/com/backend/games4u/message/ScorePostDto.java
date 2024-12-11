package com.backend.games4u.message;

public class ScorePostDto {

    private String score;

    private String gameId;

    private String userId;

    public ScorePostDto(String score, String gameId, String userId) {
        this.score = score;
        this.gameId = gameId;
        this.userId = userId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ScorePostDto{" +
                "score='" + score + '\'' +
                ", game_id='" + gameId + '\'' +
                ", user_id='" + userId + '\'' +
                '}';
    }
}
