package com.backend.games4u.payload.response;

import com.backend.games4u.message.UserImageResponseDto;
import com.backend.games4u.message.UserResponseDto;

import java.util.List;

public class UserInfoResponse {
    private Long id;
    private String username;
    private String email;
    private List<String> roles;

    private UserImageResponseDto profilePicture;

    public UserInfoResponse(Long id, String username, String email, List<String> roles, UserImageResponseDto profilePicture) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.profilePicture = profilePicture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public UserImageResponseDto getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(UserImageResponseDto profilePicture) {
        this.profilePicture = profilePicture;
    }
}
