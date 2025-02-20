package com.example.newsfeed.comment.controller;

import com.example.newsfeed.comment.application.service.CommentReadService;
import com.example.newsfeed.comment.application.service.CommentWriteService;
import com.example.newsfeed.comment.dto.request.CreateCommentRequestDto;
import com.example.newsfeed.comment.dto.request.UpdateCommnetRequestDto;
import com.example.newsfeed.comment.dto.response.GetCommentResponseDto;
import com.example.newsfeed.global.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import static com.example.newsfeed.global.common.constant.SessionConst.LOGIN_USER;
import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class CommentController {

    private final CommentWriteService commentWriteService;
    private final CommentReadService commentReadService;

    @PostMapping("/comments/feeds/{feedId}")
    public Response<Long> createComment(
        @RequestBody CreateCommentRequestDto dto,
        @SessionAttribute(LOGIN_USER) Long sessionUserId,
        @PathVariable Long feedId
    ) {
        Long newCommentId = commentWriteService.createComment(dto, sessionUserId, feedId);
        return Response.of(newCommentId, "댓글이 생성되었습니다.");
    }

    @GetMapping("/comments/feeds/{feedId}")
    public Response<Page<GetCommentResponseDto>> getCommentsByFeed(
        @PathVariable Long feedId,
        @SortDefault(sort = "updatedAt", direction = DESC) Pageable pageable
    ) {
        Page<GetCommentResponseDto> comments = commentReadService.findCommentsByFeedId(feedId, pageable);
        return Response.of(comments, "댓글이 조회되었습니다.");
    }

    @PatchMapping("/comments/{commentId}")
    public Response<Long> updateComment(
        @PathVariable Long commentId,
        @Valid @RequestBody UpdateCommnetRequestDto dto,
        @SessionAttribute(LOGIN_USER) Long sessionUserId
    ) {
        Long updatedCommentId = commentWriteService.updateComment(commentId, sessionUserId, dto);
        return Response.of(updatedCommentId, "댓글이 수정되었습니다.");
    }

    @DeleteMapping("/comments/{commentId}")
    public Response<Void> deleteComment(
        @PathVariable Long commentId,
        @SessionAttribute(LOGIN_USER) Long sessionUserId
    ) {
        commentWriteService.deleteComment(commentId, sessionUserId);
        return Response.empty("댓글이 삭제되었습니다.");
    }
}
