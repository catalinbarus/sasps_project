package com.backend.games4u.message;

public class ReviewPutDto {

    private String review;

    public ReviewPutDto() {
    }

    public ReviewPutDto(String review) {
        this.review = review;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
