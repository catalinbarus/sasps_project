package com.backend.games4u.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users",
uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true)
    private Long id;

    @NotBlank
    @Size(max = 20)
    @Column(name = "username")
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    @Column(name = "email")
    private String email;

    @NotBlank
    @Size(max = 120)
    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToOne(orphanRemoval = true, cascade = CascadeType.PERSIST, mappedBy="user")
    private UserImage userImage;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.PERSIST, mappedBy="user")
    private MyGameListConfig myGameListConfig;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.PERSIST, mappedBy="user")
    private Set<Score> scores;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.PERSIST, mappedBy="user")
    private Set<Review> reviews;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.PERSIST, mappedBy="user")
    private Set<MyGameListEntry> myGameListEntries;

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public UserImage getUserImage() {

        return userImage;
    }

    public void setUserImage(UserImage userImage) {
        this.userImage = userImage;
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

    public MyGameListConfig getMyGameListConfig() {
        return myGameListConfig;
    }

    public void setMyGameListConfig(MyGameListConfig myGameListConfig) {
        this.myGameListConfig = myGameListConfig;
    }

    public Set<MyGameListEntry> getMyGameListEntries() {
        return myGameListEntries;
    }

    public void setMyGameListEntries(Set<MyGameListEntry> myGameListEntries) {
        this.myGameListEntries = myGameListEntries;
    }
}
