package com.example.newsfeed.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateCommnetRequestDto {

    @NotBlank(message = "내용을 입력해주세요.")
    private final String content;

    public UpdateCommnetRequestDto(String content) {
        this.content = content;

    }
}
