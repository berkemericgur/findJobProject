package com.example.findjobproject.business.concretes;

import com.example.findjobproject.business.abstracts.UserService;
import com.example.findjobproject.dtos.requests.UserCreateRequest;
import com.example.findjobproject.dtos.requests.UserUpdateRequest;
import com.example.findjobproject.dtos.responses.GetAllUsersResponse;
import com.example.findjobproject.entity.User;
import com.example.findjobproject.repository.UserPagingRepository;
import com.example.findjobproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserPagingRepository userPagingRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User createUser(UserCreateRequest userCreateRequest) {

        log.debug("[{}][createRequest] -> request: {}", this.getClass().getSimpleName(), userCreateRequest);
        try {
            User user = User.builder().name(userCreateRequest.getName()).lastname(userCreateRequest.getLastname()).age(userCreateRequest.getAge()).email(userCreateRequest.getEmail()).experienceStatus(userCreateRequest.getExperienceStatus()).militaryStatus(userCreateRequest.getMilitaryStatus()).build();
            User savedUser = this.userRepository.save(user);
            log.debug("[{}][createUser] -> response: {}", this.getClass().getSimpleName(), savedUser);
            return savedUser;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<GetAllUsersResponse> getAll() {

        log.debug("[{}][getProducts]", this.getClass().getSimpleName());
        List<User> userList = this.userRepository.findAll();
        List<GetAllUsersResponse> getAllUsersResponses = convertToList(userList);
        log.debug("[{}][getProducts] -> response: {}", this.getClass().getSimpleName(), getAllUsersResponses);
        return getAllUsersResponses;
    }

    @Override
    public Optional<User> getUserById(Long id) {

        Optional<User> optionalUser = this.userRepository.findById(id);
        log.debug("[{}][getById] -> userId: {}", this.getClass().getSimpleName(), id);
        return optionalUser;

    }

    @Override
    public User updateUser(Long id, UserUpdateRequest userUpdateRequest) {

        log.debug("[{}][updateUser][id:{}] -> request: {}", this.getClass().getSimpleName(), id, userUpdateRequest);
        User user = this.userRepository.getUserById(id);
        user.setName(userUpdateRequest.getName());
        user.setLastname(userUpdateRequest.getLastname());
        user.setAge(userUpdateRequest.getAge());
        user.setEmail(userUpdateRequest.getEmail());
        user.setPhoneNumber(userUpdateRequest.getPhoneNumber());
        user.setExperienceStatus(userUpdateRequest.getExperienceStatus());
        user.setMilitaryStatus(userUpdateRequest.getMilitaryStatus());
        User userResponse = this.userRepository.save(user);
        log.debug("[{}][userResponse] -> response: {}", this.getClass().getSimpleName(), userResponse);
        return userResponse;

    }

    @Override
    public void deleteUser(Long id) {

        User user = this.userRepository.findUserById(id);
        log.debug("[{}][deleteUser] -> user: {}", this.getClass().getSimpleName(), user);
        this.userRepository.delete(user);

    }



    @Override
    public Optional<User> findUserByUsername(String username) {

        Optional<User> user = this.userRepository.findUserByUsername(username);
        return user;
    }

    @Override
    public Page<User> getAllPg(int page, int size){

        Pageable pageable = createPageable(page, size);
        return userPagingRepository.findAll(pageable);
    }

    @Override
    public Page<User> findUserByUsernamePg(String username, int page, int size){

        Pageable pageable = createPageable(page, size);
        return userPagingRepository.findByNameContaining(username, pageable);
    }


    public void registerUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
    }

    public void incrementLoginAttempts(User user) {

        int attempts = user.getLoginAttempts() + 1;
        user.setLoginAttempts(attempts);

        if (attempts >= 3) {
            user.setLocked(true);
        }

        this.userRepository.save(user);
    }

    public void resetLoginAttempts(User user) {

        user.setLoginAttempts(0);
        this.userRepository.save(user);
    }

    private Pageable createPageable(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Pageable pageable1 = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        Pageable pageable2 = PageRequest.of(page, size);

        return pageable;
    }

    private List<GetAllUsersResponse> convertToList(List<User> userList) {

        return userList.stream().map(user -> GetAllUsersResponse.builder()
                        .id(user.getId()).name(user.getName())
                        .lastname(user.getLastname())
                        .age(user.getAge())
                        .email(user.getEmail())
                        .phoneNumber(user.getPhoneNumber())
                        .experienceStatus(user.getExperienceStatus())
                        .militaryStatus(user.getMilitaryStatus())
                        .build())
                .collect(Collectors.toList());
    }
}


