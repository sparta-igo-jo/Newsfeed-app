package com.example.newsfeed.user.repository;

import com.example.newsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select u from User u where u.id = :userId")
    Optional<User> findUserById(Long userId);

    @Query(value = "select u from User u where u.deletedAt = null")
    List<User> findAllUsers();

    @Modifying
    @Query(value = "update User u set u.deletedAt = current_timestamp where u.id = :userId")
    void deleteUserById(@Param("userId") Long userId);
}
