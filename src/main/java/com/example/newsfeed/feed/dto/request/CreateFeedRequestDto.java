package com.example.newsfeed.feed.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateFeedRequestDto {

    @NotBlank(message = "제목은 필수값입니다.")
    private final String title;

    private final String contents;

    private final String feedImage;
}
