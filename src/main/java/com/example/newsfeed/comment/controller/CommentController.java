package com.example.newsfeed.comment.controller;

import com.example.newsfeed.comment.application.service.CommentService;
import com.example.newsfeed.comment.dto.request.CreateCommentRequestDto;
import com.example.newsfeed.comment.dto.request.UpdateCommnetRequestDto;
import com.example.newsfeed.comment.dto.response.GetCommentResponseDto;
import com.example.newsfeed.comment.dto.response.UpdateCommentResponseDto;
import com.example.newsfeed.global.common.constant.SessionConst;
import com.example.newsfeed.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    @PostMapping
    public Response<GetCommentResponseDto> createComment(
            @RequestBody CreateCommentRequestDto dto,
            @SessionAttribute(name = SessionConst.LOGIN_USER) Long userId,
            @RequestParam Long feedId
    ) {
        GetCommentResponseDto feedIdCreatedComment = commentService.createComment(dto, userId, feedId);
        return Response.of(feedIdCreatedComment, "댓글이 생성되었습니다.");
    }

    @GetMapping("/feeds/{feedId}")
    public Response<Page<GetCommentResponseDto>> getCommentsByFeed(
            @PathVariable Long feedId,
            @SortDefault(sort = "updateAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<GetCommentResponseDto> comments = commentService.findCommentsByFeedId(feedId, pageable);
        return Response.of(comments, "댓글이 조회되었습니다.");
    }

    @PatchMapping("/{commentId}")
    public Response<UpdateCommentResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody UpdateCommnetRequestDto dto,
            @SessionAttribute(name = SessionConst.LOGIN_USER) Long sessionUserId
    ) {
        UpdateCommentResponseDto feedIdUpdatedComment = commentService.updateComment(commentId, sessionUserId, dto);
        return Response.of(feedIdUpdatedComment, "댓글이 수정되었습니다.");
    }

    @DeleteMapping("/{commentId}")
    public Response<Void> deleteComment(
            @PathVariable Long commentId,
            @SessionAttribute(name = SessionConst.LOGIN_USER) Long sessionUserId
    ) {
        commentService.deleteComment(commentId, sessionUserId);
        return Response.empty("댓글이 삭제되었습니다.");
    }
}
