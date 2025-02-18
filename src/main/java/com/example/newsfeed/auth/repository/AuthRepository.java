package com.example.newsfeed.auth.repository;

import com.example.newsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByName(String name);

    @Query("select u from User u where u.email = :email and u.deletedAt is null")
    Optional<User> findUserByEmailAndDeletedAtIsNull(String email);
}
