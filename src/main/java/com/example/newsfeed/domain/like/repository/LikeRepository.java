package com.example.newsfeed.domain.like.repository;

import com.example.newsfeed.domain.feed.entity.Feed;
import com.example.newsfeed.domain.like.entity.Like;
import com.example.newsfeed.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserAndFeed(User user, Feed feed);

    @Modifying
    @Query("update Feed f set f.likeCount = f.likeCount - 1 " +
        "where f.id in (select l.feed.id from Like l where l.user.id = :userId)")
    void decreaseLikesOfFeeds(@Param("userId") Long userId);
}