package com.example.newsfeed.domain.comment.application.converter;

import com.example.newsfeed.domain.comment.dto.response.GetCommentResponseDto;
import com.example.newsfeed.domain.comment.entity.Comment;
import org.springframework.data.domain.Page;

public class CommentConverter {

    public static GetCommentResponseDto toResponse(Comment comment) {
        return GetCommentResponseDto.of(comment);
    }

    public static Page<GetCommentResponseDto> toResponse(Page<Comment> comment) {
        return comment.map(GetCommentResponseDto::of);
    }
}
