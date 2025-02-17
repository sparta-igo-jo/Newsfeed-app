package com.example.newsfeed.comment.dto.response;

public class GetCommentIdResponseDto {
    private final Long id;

    public GetCommentIdResponseDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
