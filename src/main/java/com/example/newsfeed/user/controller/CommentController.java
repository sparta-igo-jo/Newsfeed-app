package com.example.newsfeed.user.controller;

import com.example.newsfeed.user.application.service.CommentService;
import com.example.newsfeed.user.dto.request.CreateCommentRequestDto;
import com.example.newsfeed.user.dto.response.GetCommentResponseDto;
import com.example.newsfeed.user.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 새로운 댓글 작성
     */
    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CreateCommentRequestDto requestDto) {
        Comment savedComment = commentService.createComment(requestDto);
        return ResponseEntity.ok(savedComment);
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 특정 피드의 댓글 조회 (페이징 처리)
     */
    @GetMapping("/feed/{feedsId}")
    public ResponseEntity<Page<GetCommentResponseDto>> getCommentsByFeed(@PathVariable Long feedsId, Pageable pageable) {
        return ResponseEntity.ok(commentService.findCommentsByFeedId(feedsId, pageable));
    }
}
