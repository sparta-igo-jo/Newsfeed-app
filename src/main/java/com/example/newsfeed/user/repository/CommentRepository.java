package com.example.newsfeed.user.repository;

import com.example.newsfeed.user.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
