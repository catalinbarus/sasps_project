package com.backend.games4u.models;

import jakarta.persistence.*;

import java.util.Arrays;

@Entity
@Table(name = "image_table")
public class ImageDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", unique = true)
    private long id;

    @Column(name = "image_name")
    private String name;

    @Column(name = "image_type")
    private String type;

    @Lob
    @Column(name = "data", columnDefinition="BLOB")
    private byte[] data;

    @ManyToOne
    @JoinColumn(name="game_id", nullable=false)
    private VideoGame videoGame;

    public ImageDB() {

    }

    public ImageDB(String name, String type, byte[] data, VideoGame videoGame) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.videoGame = videoGame;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public VideoGame getVideoGame() {
        return videoGame;
    }

    public void setVideoGame(VideoGame videoGame) {
        this.videoGame = videoGame;
    }

    @Override
    public String toString() {
        return "ImageDB{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", data=" + Arrays.toString(data) +
                ", videoGame=" + videoGame +
                '}';
    }
}
