package com.backend.games4u.message;

public class ImageResponseDto {

    private long id;

    private String name;
    private String url;
    private String type;
    private long size;

    private long gameId;
    private String convertedData;

    public ImageResponseDto(long id, String name, String url, String type, long size, long gameId, String convertedData) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.type = type;
        this.size = size;
        this.gameId = gameId;
        this.convertedData = convertedData;
    }

    public ImageResponseDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public String getConvertedData() {
        return convertedData;
    }

    public void setConvertedData(String convertedData) {
        this.convertedData = convertedData;
    }
}
