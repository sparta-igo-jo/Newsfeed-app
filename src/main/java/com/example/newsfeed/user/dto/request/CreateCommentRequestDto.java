package com.example.newsfeed.user.dto.request;

import lombok.Getter;

@Getter
public class CreateCommentRequestDto {

    private final String content;
    private final Long userId;
    private final Long feedId;


    public CreateCommentRequestDto(String content, Long userId, Long feedId) {
        this.content = content;
        this.userId = userId;
        this.feedId = feedId;
    }
}
