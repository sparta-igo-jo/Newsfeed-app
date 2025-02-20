package com.example.newsfeed.domain.feed.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateFeedRequestDto {

    @NotBlank
    private final String title;

    private final String contents;

    private final String feedImage;
}
