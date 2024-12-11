package com.backend.games4u.message;

public class ImageResponseMessage {

    private String message;

    public ImageResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
