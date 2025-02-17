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
    private final LocalDateTime createdDate;
    private final LocalDateTime updatedDate;
    private final String username;
    private final Long feedId;

    public static GetCommentResponseDto of(Long id, String content, LocalDateTime createdAt,
                                           LocalDateTime updatedAt, String userName, Long feedId) {
        return new GetCommentResponseDto(id, content, createdAt, updatedAt, userName, feedId);
    }

    @Deprecated
    public static GetCommentResponseDto fromEntity(Comment comment) {
        return CommentConverter.toDto(comment);
    }
}
