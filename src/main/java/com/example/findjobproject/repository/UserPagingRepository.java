package com.example.findjobproject.repository;

import com.example.findjobproject.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserPagingRepository extends PagingAndSortingRepository<User, Long> {

    @Query("select u from User u")
    Page<User> getAllUsers(Pageable pageable);
    Page<User> findByNameContaining(String name, Pageable pageable);

}
