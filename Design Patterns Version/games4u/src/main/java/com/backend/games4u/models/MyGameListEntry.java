package com.backend.games4u.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "my_game_list")
public class MyGameListEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "my_game_list_id", unique = true)
    private long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="game_id", nullable=false)
    private VideoGame videoGame;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @OneToOne
    @JoinColumn(name = "status_id", referencedColumnName = "status_id")
    private MyGameListStatus myGameListStatus;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "created_at")
    private Date createdAt;

    public MyGameListEntry(VideoGame videoGame, User user, MyGameListStatus myGameListStatus, Date createdAt) {
        this.videoGame = videoGame;
        this.user = user;
        this.myGameListStatus = myGameListStatus;
        this.createdAt = createdAt;
    }

    public MyGameListEntry() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public MyGameListStatus getMyGameListStatus() {
        return myGameListStatus;
    }

    public void setMyGameListStatus(MyGameListStatus myGameListStatus) {
        this.myGameListStatus = myGameListStatus;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "MyGameListEntry{" +
                "id=" + id +
                ", videoGame=" + videoGame +
                ", user=" + user +
                ", myGameListStatus=" + myGameListStatus +
                ", createdAt=" + createdAt +
                '}';
    }
}
