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
    private final String username;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;


    public static GetCommentResponseDto of(Comment comment) {
        return new GetCommentResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getName(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
