package com.backend.games4u.message;

public class VideoGamePostDto {

    private String name;

    private long developerId;

    private long publisherId;

    private String releaseDate;

    private long genreId;

    private String rating;

    private String description;

    public VideoGamePostDto() {
    }

    public VideoGamePostDto(String name, long developerId, long publisherId, String releaseDate, long genreId, String rating, String description) {
        this.name = name;
        this.developerId = developerId;
        this.publisherId = publisherId;
        this.releaseDate = releaseDate;
        this.genreId = genreId;
        this.rating = rating;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(long developerId) {
        this.developerId = developerId;
    }

    public long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(long publisherId) {
        this.publisherId = publisherId;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public long getGenreId() {
        return genreId;
    }

    public void setGenreId(long genreId) {
        this.genreId = genreId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
