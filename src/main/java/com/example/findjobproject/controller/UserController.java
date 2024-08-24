package com.example.findjobproject.controller;

import com.example.findjobproject.business.abstracts.UserService;
import com.example.findjobproject.dtos.requests.UserCreateRequest;
import com.example.findjobproject.dtos.requests.UserUpdateRequest;
import com.example.findjobproject.dtos.responses.GetAllUsersResponse;
import com.example.findjobproject.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/api/users/getAll")
    public ResponseEntity<List<GetAllUsersResponse>> getAll(){
        log.debug("[{}][getAll] ",this.getClass().getSimpleName());
        return new ResponseEntity<>(this.userService.getAll(),HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/api/users/getAllPg")
    public ResponseEntity<Page<User>> getAllPg(@RequestParam(defaultValue = "0", value = "page") int page,
                               @RequestParam(defaultValue = "5", value = "size") int size){

        log.debug("[{}][getAllPg] -> {} {} ", this.getClass().getSimpleName(), page, size);
        return new ResponseEntity<>(this.userService.getAllPg(page, size), HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/api/users/getByUsernamePg")
    public ResponseEntity<Page<User>> findByUsernamePg(@RequestParam(required = false, value = "username") String username,
                                                       @RequestParam(defaultValue = "0", value = "page" ) int page,
                                                       @RequestParam(defaultValue = "5", value = "size") int size){

        log.debug("[{}][findByUsernamePg] -> request: {} {} {} ", this.getClass().getSimpleName(), username, page, size);
        return new ResponseEntity<>(this.userService.findUserByUsernamePg(username, page, size), HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/api/users/getById/{id}")
    public ResponseEntity<Optional<User>> getById(@PathVariable("id") Long id){

        log.debug("[{}][getById] -> request: {}", this.getClass(), id);
        return new ResponseEntity<>(this.userService.getUserById(id) ,HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/api/users/getByUsername")
    public ResponseEntity<Optional<User>> findByUsername(@RequestParam String username){

        log.debug("[{}][findByUsername] -> request: {}",this.getClass().getSimpleName(), username);
        return new ResponseEntity<>(this.userService.findUserByUsername(username), HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/api/users/create")
    public ResponseEntity<User> createUser(@RequestBody UserCreateRequest userCreateRequest){


        return new ResponseEntity<>(this.userService.createUser(userCreateRequest),HttpStatus.CREATED);
    }

    @PutMapping(value = "/api/users/update/{id}")
    public ResponseEntity<User> updateUser(@RequestBody UserUpdateRequest userUpdateRequest, @PathVariable Long id){

        return new ResponseEntity<>(this.userService.updateUser(id, userUpdateRequest), HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/users/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("id") Long id){

        this.userService.deleteUser(id);
    }
}
