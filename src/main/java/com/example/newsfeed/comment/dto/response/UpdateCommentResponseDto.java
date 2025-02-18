package com.example.newsfeed.comment.dto.response;

import com.example.newsfeed.comment.entity.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UpdateCommentResponseDto {

    private final Long id;
    private final String content;
    private final LocalDateTime updatedAt;

    public static UpdateCommentResponseDto of(Comment comment) {
        return new UpdateCommentResponseDto(comment.getId(), comment.getContent(), comment.getUpdatedAt());
    }
}
