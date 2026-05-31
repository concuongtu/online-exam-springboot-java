package com.example.onlineexam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.onlineexam.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsernameAndPassword(String username, String password);

    List<User> findByUsernameContainingIgnoreCaseOrFullnameContainingIgnoreCase(
            String username,
            String fullname
    );
}