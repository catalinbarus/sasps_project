package com.backend.games4u.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "video_games")
public class VideoGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id", unique = true)
    private long id;

    @Column(name = "game_name")
    private String name;

    @OneToOne
    @JoinColumn(name = "developer_id", referencedColumnName = "developer_id")
    private GameDeveloper gameDeveloper;

    @OneToOne
    @JoinColumn(name = "publisher_id", referencedColumnName = "publisher_id")
    private GamePublisher gamePublisher;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "release_date")
    private Date releaseDate;

    @OneToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "genre_id")
    private Genre genre;

    @Column(name = "rating")
    private String rating;

    @Column(name = "description")
    private String description;

    @Column(name = "average_score")
    private Double averageScore;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.PERSIST, mappedBy="videoGame")
    private Set<ImageDB> images;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.PERSIST, mappedBy="videoGame")
    private Set<Trailer> trailers;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.PERSIST, mappedBy="videoGame")
    private Set<Score> scores;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.PERSIST, mappedBy="videoGame")
    private Set<Review> reviews;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.PERSIST, mappedBy="videoGame")
    private Set<MyGameListEntry> myGameListEntries;

    public VideoGame() {

    }

    public VideoGame(long id, String name, GameDeveloper gameDeveloper,
                     GamePublisher gamePublisher, Date releaseDate, Genre genre, String rating,
                     String description, Double averageScore, Set<ImageDB> images, Set<Trailer> trailers,
                     Set<Score> scores, Set<Review> reviews) {
        this.id = id;
        this.name = name;
        this.gameDeveloper = gameDeveloper;
        this.gamePublisher = gamePublisher;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.rating = rating;
        this.description = description;
        this.averageScore = averageScore;
        this.images = images;
        this.trailers = trailers;
        this.scores = scores;
        this.reviews = reviews;
    }

    public VideoGame(String name, GameDeveloper gameDeveloper, GamePublisher gamePublisher,
                     Date releaseDate, Genre genre, String rating, String description, Double averageScore,
                     Set<ImageDB> images, Set<Trailer> trailers, Set<Score> scores, Set<Review> reviews) {
        this.name = name;
        this.gameDeveloper = gameDeveloper;
        this.gamePublisher = gamePublisher;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.rating = rating;
        this.description = description;
        this.averageScore = averageScore;
        this.images = images;
        this.trailers = trailers;
        this.scores = scores;
        this.reviews = reviews;
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

    public Set<ImageDB> getImages() {
        return images;
    }

    public void setImages(Set<ImageDB> images) {
        this.images = images;
    }

    public Set<Score> getScores() {
        return scores;
    }

    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }

    public Set<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(Set<Trailer> trailers) {
        this.trailers = trailers;
    }

    public Set<MyGameListEntry> getMyGameListEntries() {
        return myGameListEntries;
    }

    public void setMyGameListEntries(Set<MyGameListEntry> myGameListEntries) {
        this.myGameListEntries = myGameListEntries;
    }

    @Override
    public String toString() {
        return "VideoGame{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gameDeveloper=" + gameDeveloper +
                ", gamePublisher=" + gamePublisher +
                ", releaseDate=" + releaseDate +
                ", genre=" + genre +
                ", rating='" + rating + '\'' +
                ", description='" + description + '\'' +
                ", averageScore=" + averageScore +
                ", images=" + images +
                ", trailers=" + trailers +
                ", scores=" + scores +
                ", reviews=" + reviews +
                ", myGameListEntries=" + myGameListEntries +
                '}';
    }
}
