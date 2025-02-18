package com.example.newsfeed.feed.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateFeedRequestDto {

    @NotBlank
    private final String tile;

    private final String contents;

    @Nullable
    private final String feedImage;
}
