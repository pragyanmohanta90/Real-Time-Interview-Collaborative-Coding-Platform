package com.interviewplatform.backend.dto;

public class UserResponse {

    private String id;

    private String name;

    private String email;

    private String role;

    private String title;

    private String location;

    private String avatar;

    private String about;

    public UserResponse() {
    }

    public UserResponse(
            String id,
            String name,
            String email,
            String role,
            String title,
            String location,
            String avatar,
            String about
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.title = title;
        this.location = location;
        this.avatar = avatar;
        this.about = about;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}