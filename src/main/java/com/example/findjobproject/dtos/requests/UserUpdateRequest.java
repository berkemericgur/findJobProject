package com.example.findjobproject.dtos.requests;

import lombok.Data;

@Data
public class UserUpdateRequest {

    private Long id;
    private String name;
    private String lastname;
    private Integer phoneNumber;
    private Integer age;
    private String email;
    private String militaryStatus;
    private String experienceStatus;
}
