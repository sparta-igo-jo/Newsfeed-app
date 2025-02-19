package com.example.newsfeed.feed.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@RequiredArgsConstructor
public class UpdateFeedRequestDto {

    @NotBlank(message = "제목은 필수값입니다.")
    private final String title;

    @Nullable
    @JsonInclude(NON_NULL)
    private final String contents;

    @Nullable
    @JsonInclude(NON_NULL)
    private final String feedImage;
}
