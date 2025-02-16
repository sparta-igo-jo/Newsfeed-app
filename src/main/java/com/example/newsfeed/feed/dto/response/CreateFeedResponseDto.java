package com.example.newsfeed.feed.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CreateFeedResponseDto {

    private final Long feedId;

    private final String title;

    private final String contents;

    private final String feedImage;

    private final Long likeCount;

    private final LocalDateTime updatedAt;

    public static CreateFeedResponseDto of(
            Long feedId,
            String title,
            String contents,
            String feedImage,
            Long likeCount,
            LocalDateTime updatedAt
    ) {
        return new CreateFeedResponseDto(feedId, title, contents, feedImage, likeCount, updatedAt);
    }
}
