package com.example.newsfeed.user.controller;

import com.example.newsfeed.user.application.service.CommentService;
import com.example.newsfeed.user.dto.request.CreateCommentRequestDto;
import com.example.newsfeed.user.dto.response.GetCommentResponseDto;
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
     * 댓글 생성
     */
    @PostMapping
    public ResponseEntity<String> createComment(@RequestBody CreateCommentRequestDto requestDto,
                                                @RequestParam Long userId,
                                                @RequestParam Long feedId) {
        commentService.createComment(requestDto, userId, feedId);
        return ResponseEntity.ok("댓글이 등록되었습니다.");
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, @RequestParam Long userId) {
        commentService.deleteComment(commentId, userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 특정 피드의 댓글 조회 (페이징 처리)
     */
    @GetMapping("/feeds/{feedId}")
    public ResponseEntity<Page<GetCommentResponseDto>> getCommentsByFeed(@PathVariable Long feedId, Pageable pageable) {
        return ResponseEntity.ok(commentService.findCommentsByFeedId(feedId, pageable));
    }
}
