package com.example.newsfeed.user.repository;

import com.example.newsfeed.user.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByFeedsId(Long feedsId, Pageable pageable);
}
