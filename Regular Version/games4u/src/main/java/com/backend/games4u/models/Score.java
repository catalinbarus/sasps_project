package com.backend.games4u.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "scores")
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "score_id", unique = true)
    private long id;

    @Column(name = "score")
    private long score;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="game_id", nullable=false)
    private VideoGame videoGame;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    public Score() {

    }

    public Score(long score, VideoGame videoGame, User user) {
        this.score = score;
        this.videoGame = videoGame;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public VideoGame getVideoGame() {
        return videoGame;
    }

    public void setVideoGame(VideoGame videoGame) {
        this.videoGame = videoGame;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", score=" + score +
                ", videoGame=" + videoGame +
                ", user=" + user +
                '}';
    }
}
