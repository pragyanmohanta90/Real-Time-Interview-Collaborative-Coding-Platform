// package com.interviewplatform.backend.bot.entity;

// import lombok.AllArgsConstructor;
// import lombok.Data;
// import lombok.NoArgsConstructor;
// import org.springframework.data.annotation.Id;
// import org.springframework.data.mongodb.core.index.Indexed;
// import org.springframework.data.mongodb.core.mapping.Document;

// import java.time.Instant;

// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// @Document(collection = "users")
// public class User {

//     @Id
//     private String id;

//     private String name;

//     @Indexed(unique = true)
//     private String email;

//     /**
//      * NOTE: Per project requirements this app uses a lightweight auth scheme.
//      * The password is stored using a simple SHA-256 hash (see AuthService) —
//      * not BCrypt, not JWT, no sessions/cookies.
//      */
//     private String password;

//     private Instant createdAt;
// }
