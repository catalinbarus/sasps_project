package com.backend.games4u.message;

public class ReviewPostDto {

    private String review;

    private String gameId;

    private String userId;

    public ReviewPostDto(String review, String gameId, String userId) {
        this.review = review;
        this.gameId = gameId;
        this.userId = userId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
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
        return "ReviewPostDto{" +
                "review='" + review + '\'' +
                ", gameId='" + gameId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
