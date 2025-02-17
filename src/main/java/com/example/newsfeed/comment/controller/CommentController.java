package com.example.newsfeed.comment.controller;

import com.example.newsfeed.comment.application.service.CommentService;
import com.example.newsfeed.comment.dto.request.CreateCommentRequestDto;
import com.example.newsfeed.comment.dto.response.GetCommentResponseDto;
import com.example.newsfeed.comment.dto.response.GetCommentResponseWrapperDto;
import com.example.newsfeed.global.common.Const.SessionConst;
import com.example.newsfeed.global.response.Response;
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
    public ResponseEntity<String> createComment(
            @RequestBody CreateCommentRequestDto requestDto,
            @SessionAttribute(name = SessionConst.LOGIN_USER) Long userId,
            @RequestParam Long feedId
    ) {
        commentService.createComment(requestDto, userId, feedId);
        return ResponseEntity.ok("댓글이 등록되었습니다.");
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, @SessionAttribute(name = SessionConst.LOGIN_USER) Long userId) {
        commentService.deleteComment(commentId, userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 특정 피드의 댓글 조회 (페이징 처리)
     */
    @GetMapping("/feeds/{feedId}")
    public ResponseEntity<GetCommentResponseWrapperDto<Page<GetCommentResponseDto>>> getCommentsByFeed(
            @PathVariable Long feedId, Pageable pageable) {
        Page<GetCommentResponseDto> comments = commentService.findCommentsByFeedId(feedId, pageable);
        return ResponseEntity.ok(GetCommentResponseWrapperDto.of(comments));
    }
}
