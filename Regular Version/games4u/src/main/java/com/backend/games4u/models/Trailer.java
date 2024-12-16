package com.backend.games4u.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "trailers")
public class Trailer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trailer_id", unique = true)
    private long id;

    @Column(name = "youtube_id")
    private String youtubeId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="game_id", nullable=false)
    private VideoGame videoGame;

    public Trailer() {

    }

    public Trailer(String youtubeId, VideoGame videoGame) {
        this.youtubeId = youtubeId;
        this.videoGame = videoGame;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getYoutubeId() {
        return youtubeId;
    }

    public void setYoutubeId(String youtubeId) {
        this.youtubeId = youtubeId;
    }

    public VideoGame getVideoGame() {
        return videoGame;
    }

    public void setVideoGame(VideoGame videoGame) {
        this.videoGame = videoGame;
    }

    @Override
    public String toString() {
        return "Trailer{" +
                "id=" + id +
                ", youtubeId='" + youtubeId + '\'' +
                ", videoGame=" + videoGame +
                '}';
    }
}
