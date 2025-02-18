package com.example.newsfeed.comment.repository;

import com.example.newsfeed.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByFeedId(Long feedId, Pageable pageable);

    List<Comment> findByUserId(Long userId);

    @Query("SELECT c.user.id FROM Comment c WHERE c.id = :commentId")
    Long findUserIdByCommentId(@Param("commentId") Long commentId);
}
