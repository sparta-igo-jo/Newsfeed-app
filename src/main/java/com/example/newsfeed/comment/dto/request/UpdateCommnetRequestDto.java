package com.example.newsfeed.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateCommnetRequestDto {

    @NotBlank
    private final String content;
}