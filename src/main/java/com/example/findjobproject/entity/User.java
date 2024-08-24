package com.example.findjobproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Builder.Default
    private boolean locked = false;
    @Builder.Default
    private int loginAttempts = 0;

    @Column(name = "name", length = 30)
    private String name;

    @Column(name = "last_name", length = 30)
    private String lastname;

    @Column(name = "phone_number", length = 11)
    private Integer phoneNumber;

    @Column(name = "age")
    private Integer age;

    @Column(name = "email")
    private String email;

    @Column(name = "military_status", length = 10)
    private String militaryStatus;

    @Column(name = "experience_status")
    private String experienceStatus;








}
