package com.example.newsfeed.like.repository;

import com.example.newsfeed.like.entity.Follow;
import com.example.newsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
   Optional<Follow> findByFollowerAndFollowing(User follower, User following);


}
