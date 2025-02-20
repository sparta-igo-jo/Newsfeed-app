package com.example.newsfeed.domain.user.repository;

import com.example.newsfeed.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select u from User u where u.id = :userId and u.deletedAt is null")
    Optional<User> findUserById(Long userId);

    @Query(value = "select u from User u where u.name like concat('%', :keyword, '%') and u.deletedAt is null")
    Page<User> findAllUsersByKeyword(String keyword, Pageable pageable);

    @Modifying
    @Query(value = "update User u set u.deletedAt = current_timestamp where u.id = :userId")
    void deleteUserById(@Param("userId") Long userId);

    Page<User> findByIdIn(Collection<Long> ids, Pageable pageable);
}
