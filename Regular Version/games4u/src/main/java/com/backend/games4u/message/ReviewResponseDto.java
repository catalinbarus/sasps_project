package com.backend.games4u.message;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ReviewResponseDto {

    private long id;

    private String review;

    private long user_id;

    private long game_id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;


    public ReviewResponseDto(long id, String review, long user_id, long game_id, Date timestamp) {
        this.id = id;
        this.review = review;
        this.user_id = user_id;
        this.game_id = game_id;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getGame_id() {
        return game_id;
    }

    public void setGame_id(long game_id) {
        this.game_id = game_id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ReviewResponseDto{" +
                "id=" + id +
                ", review='" + review + '\'' +
                ", user_id=" + user_id +
                ", game_id=" + game_id +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
