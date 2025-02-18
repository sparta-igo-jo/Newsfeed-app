package com.example.newsfeed.comment.application.converter;

import com.example.newsfeed.comment.dto.response.GetCommentResponseDto;
import com.example.newsfeed.comment.entity.Comment;

public class CommentConverter {

    /**
     * Comment 엔티티를 GetCommentResponseDto 로 변환
     */
    public static GetCommentResponseDto toResponse(Comment comment) {
        return GetCommentResponseDto.of(comment);
    }
}
