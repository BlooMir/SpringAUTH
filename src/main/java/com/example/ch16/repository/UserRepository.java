package com.example.ch16.repository;


import com.example.ch16.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User getByUid(String uid);

}