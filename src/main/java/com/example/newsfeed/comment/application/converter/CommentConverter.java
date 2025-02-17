package com.example.newsfeed.comment.application.converter;

import com.example.newsfeed.comment.dto.response.GetCommentResponseDto;
import com.example.newsfeed.comment.entity.Comment;

public class CommentConverter {

    /**
     * Comment 엔티티를 GetCommentResponseDto 로 변환
     */
    public static GetCommentResponseDto toDto(Comment comment) {
        if (comment == null) {
            return null;
        }

        return GetCommentResponseDto.of(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                comment.getUser() != null ? comment.getUser().getName() : "알수 없음",
                comment.getFeed() != null ? comment.getFeed().getId() : null
        );
    }
}
