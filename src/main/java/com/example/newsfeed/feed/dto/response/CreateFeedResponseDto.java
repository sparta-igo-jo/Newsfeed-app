package com.example.newsfeed.feed.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CreateFeedResponseDto {

    private final long feedId;

    private final String title;

    private final String contents;

    private final String feedImage;

    private final LocalDateTime updatedAt;
}
