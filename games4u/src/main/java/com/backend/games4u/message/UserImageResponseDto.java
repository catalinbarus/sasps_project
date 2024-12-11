package com.backend.games4u.message;

public class UserImageResponseDto {

    private String name;

    private String url;

    private String type;

    private long size;

    private long userId;

    private String convertedData;

    public UserImageResponseDto(String name, String url, String type, long size, long userId, String convertedData) {
        this.name = name;
        this.url = url;
        this.type = type;
        this.size = size;
        this.userId = userId;
        this.convertedData = convertedData;
    }

    public UserImageResponseDto() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getConvertedData() {
        return convertedData;
    }

    public void setConvertedData(String convertedData) {
        this.convertedData = convertedData;
    }
}
