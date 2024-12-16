package com.backend.games4u.message;

public class MyGameListConfigResponseDto {

    private Long id;

    private String backgroundColor;

    private String textColor;

    private Long userId;

    public MyGameListConfigResponseDto(Long id, String backgroundColor, String textColor, Long userId) {
        this.id = id;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.userId = userId;
    }

    public MyGameListConfigResponseDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
