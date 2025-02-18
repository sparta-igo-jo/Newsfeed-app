package com.example.newsfeed.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateCommentRequestDto {

    @NotBlank(message = "내용을 입력해주세요.")
    private final String content;

    @NotNull
    private final Long userId;

    @NotNull
    private final Long feedId;

    public CreateCommentRequestDto(String content, Long userId, Long feedId) {
        this.content = content;
        this.userId = userId;
        this.feedId = feedId;
    }
}
