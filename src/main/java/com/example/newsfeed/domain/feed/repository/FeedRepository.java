package com.example.newsfeed.domain.feed.repository;

import com.example.newsfeed.domain.feed.entity.Feed;
import com.example.newsfeed.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    Page<Feed> findByUserIdIn(Collection<Long> userIds, Pageable pageable);

    Page<Feed> findByUser(User user, Pageable pageable);

    void deleteByUserId(Long userId);
}
