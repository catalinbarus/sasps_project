package com.backend.games4u.message;

import com.backend.games4u.models.GameDeveloper;
import com.backend.games4u.models.GamePublisher;
import com.backend.games4u.models.Genre;
import com.backend.games4u.models.Score;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class VideoGameResponseDto {

    private long id;

    private String name;

    private GameDeveloper gameDeveloper;

    private GamePublisher gamePublisher;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date releaseDate;

    private Genre genre;

    private String rating;

    private String description;

    private List<ImageResponseDto> images;
    private String averageScore;

    private Set<ScoreResponseDto> scores;

    private Set<ReviewResponseDto> reviews;

    private Set<TrailerResponseDto> trailers;

    public VideoGameResponseDto(long id, String name, GameDeveloper gameDeveloper,
                                GamePublisher gamePublisher, Date releaseDate, Genre genre,
                                String rating, String description, List<ImageResponseDto> images, String averageScore, Set<ScoreResponseDto> scores, Set<ReviewResponseDto> reviews, Set<TrailerResponseDto> trailers) {
        this.id = id;
        this.name = name;
        this.gameDeveloper = gameDeveloper;
        this.gamePublisher = gamePublisher;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.rating = rating;
        this.description = description;
        this.images = images;
        this.averageScore = averageScore;
        this.scores = scores;
        this.reviews = reviews;
        this.trailers = trailers;
    }

    public VideoGameResponseDto() {
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

    public GameDeveloper getGameDeveloper() {
        return gameDeveloper;
    }

    public void setGameDeveloper(GameDeveloper gameDeveloper) {
        this.gameDeveloper = gameDeveloper;
    }

    public GamePublisher getGamePublisher() {
        return gamePublisher;
    }

    public void setGamePublisher(GamePublisher gamePublisher) {
        this.gamePublisher = gamePublisher;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
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

    public List<ImageResponseDto> getImages() {
        return images;
    }

    public void setImages(List<ImageResponseDto> images) {
        this.images = images;
    }

    public String getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(String averageScore) {
        this.averageScore = averageScore;
    }

    public Set<ScoreResponseDto> getScores() {
        return scores;
    }

    public void setScores(Set<ScoreResponseDto> scores) {
        this.scores = scores;
    }

    public Set<ReviewResponseDto> getReviews() {
        return reviews;
    }

    public void setReviews(Set<ReviewResponseDto> reviews) {
        this.reviews = reviews;
    }

    public Set<TrailerResponseDto> getTrailers() {
        return trailers;
    }

    public void setTrailers(Set<TrailerResponseDto> trailers) {
        this.trailers = trailers;
    }
}
