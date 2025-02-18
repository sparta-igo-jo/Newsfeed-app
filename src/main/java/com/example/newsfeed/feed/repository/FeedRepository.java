package com.example.newsfeed.feed.repository;

import com.example.newsfeed.feed.entity.Feed;
import com.example.newsfeed.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    Page<Feed> findByUserIdIn(Collection<Long> userIds, Pageable pageable);

    Page<Feed> findByUser(User user, Pageable pageable);

    List<Feed> findFeedByUser_Id(Long userId);

}
