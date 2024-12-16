package com.backend.games4u.message;

import com.backend.games4u.models.Role;

import java.util.HashSet;
import java.util.Set;

public class UserResponseDto {

    private long id;

    private String username;

    private String email;

    private Set<Role> roles = new HashSet<>();

    private UserImageResponseDto profilePicture;

    private MyGameListConfigResponseDto myGameListConfig;

    private Set<MyGameListEntryResponseDto> myGameListEntries;

    public UserResponseDto(long id, String username, String email, Set<Role> roles, UserImageResponseDto profilePicture, MyGameListConfigResponseDto myGameListConfig, Set<MyGameListEntryResponseDto> myGameListEntries) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.profilePicture = profilePicture;
        this.myGameListConfig = myGameListConfig;
        this.myGameListEntries = myGameListEntries;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public UserImageResponseDto getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(UserImageResponseDto profilePicture) {
        this.profilePicture = profilePicture;
    }

    public MyGameListConfigResponseDto getMyGameListConfig() {
        return myGameListConfig;
    }

    public void setMyGameListConfig(MyGameListConfigResponseDto myGameListConfig) {
        this.myGameListConfig = myGameListConfig;
    }

    public Set<MyGameListEntryResponseDto> getMyGameListEntries() {
        return myGameListEntries;
    }

    public void setMyGameListEntries(Set<MyGameListEntryResponseDto> myGameListEntries) {
        this.myGameListEntries = myGameListEntries;
    }
}
