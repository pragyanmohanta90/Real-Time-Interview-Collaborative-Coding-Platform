package com.interviewplatform.backend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id;

    private String name;

    private String email;

    private String role;

    private String password;

    // Profile Fields
    private String title;

    private String location;

    private String about;

    private String avatar;

    private Integer readinessScore = 0;

    private List<String> skills = new ArrayList<>();

    private List<String> targets = new ArrayList<>();

    private List<Experience> experience = new ArrayList<>();
}