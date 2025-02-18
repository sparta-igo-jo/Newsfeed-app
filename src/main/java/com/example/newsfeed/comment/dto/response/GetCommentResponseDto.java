package com.example.newsfeed.comment.dto.response;

import com.example.newsfeed.comment.application.converter.CommentConverter;
import com.example.newsfeed.comment.entity.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class GetCommentResponseDto {

    private final Long id;
    private final String content;
    private final LocalDateTime updatedDate;
    private final String username;

    public static GetCommentResponseDto of(Comment comment) {
        return new GetCommentResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUser().getName()
        );
    }
}
