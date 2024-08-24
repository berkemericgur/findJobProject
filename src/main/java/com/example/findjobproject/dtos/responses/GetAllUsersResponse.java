package com.example.findjobproject.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllUsersResponse {

    private Long id;
    private String name;
    private String lastname;
    private Integer phoneNumber;
    private Integer age;
    private String email;
    private String militaryStatus;
    private String experienceStatus;
}
