package com.example.newsfeed.like.repository;

import com.example.newsfeed.feed.entity.Feed;
import com.example.newsfeed.like.entity.Like;
import com.example.newsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long> {
  Optional<Like> findByUserAndFeed(User user, Feed feed);
}
