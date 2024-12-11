package com.backend.games4u.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "requests")
public class GameRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id", unique = true)
    private long id;

    @Column(name = "game_name")
    private String gameName;

    @Column(name = "link")
    private String link;

    @Column(name = "details")
    private String details;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "created_at")
    private Date createdAt;

    public GameRequest() {

    }

    public GameRequest(String gameName, String link, String details, User user, Date createdAt) {
        this.gameName = gameName;
        this.link = link;
        this.details = details;
        this.user = user;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "GameRequest{" +
                "id=" + id +
                ", gameName='" + gameName + '\'' +
                ", link='" + link + '\'' +
                ", details='" + details + '\'' +
                ", user=" + user +
                ", createdAt=" + createdAt +
                '}';
    }
}
