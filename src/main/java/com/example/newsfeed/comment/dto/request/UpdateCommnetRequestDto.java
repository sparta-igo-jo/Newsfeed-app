package com.example.newsfeed.comment.dto.request;

import lombok.Getter;

@Getter
public class UpdateCommnetRequestDto {

    private final String content;

    public UpdateCommnetRequestDto(String content) {
        this.content = content;

    }
}
