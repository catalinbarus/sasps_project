package com.backend.games4u.models;

import jakarta.persistence.*;

@Entity
@Table(name = "game_list_status")
public class MyGameListStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id", unique = true)
    private Long id;

    @Column(name = "status_name")
    private String status;

    public MyGameListStatus(String status) {
        this.status = status;
    }

    public MyGameListStatus() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MyGameListStatus{" +
                "id=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}
