package com.example.findjobproject.business.abstracts;

import com.example.findjobproject.dtos.requests.UserCreateRequest;
import com.example.findjobproject.dtos.requests.UserUpdateRequest;
import com.example.findjobproject.dtos.responses.GetAllUsersResponse;
import com.example.findjobproject.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User createUser(UserCreateRequest userCreateRequest);
    Page<User> getAllPg(int page, int size);
    Page<User> findUserByUsernamePg(String username, int page, int size);
    List<GetAllUsersResponse> getAll();
    Optional<User> getUserById(Long id);
    User updateUser(Long id, UserUpdateRequest userUpdateRequest);
    void deleteUser(Long id);
    Optional<User> findUserByUsername(String username);


}
