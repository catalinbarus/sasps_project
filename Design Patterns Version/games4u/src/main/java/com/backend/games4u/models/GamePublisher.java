package com.backend.games4u.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "game_publishers")
public class GamePublisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publisher_id", unique = true)
    private long id;

    @Column(name = "publisher_name")
    private String name;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "establish_date")
    private Date establishDate;

    public GamePublisher() {

    }

    public GamePublisher(String name, Date establishDate) {
        this.name = name;
        this.establishDate = establishDate;
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

    public Date getEstablishDate() {
        return establishDate;
    }

    public void setEstablishDate(Date establishDate) {
        this.establishDate = establishDate;
    }

    @Override
    public String toString() {
        return "GamePublishers{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", establishDate=" + establishDate +
                '}';
    }
}
