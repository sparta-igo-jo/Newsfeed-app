package com.example.newsfeed.feed.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class GetFeedResponseDto {

    private final long feedId;

    private final String title;

    private final String contents;

    private final String feedImage;

    private final Long likeCount;

    private final LocalDateTime updatedAt;

    public static GetFeedResponseDto of(
            Long feedId,
            String title,
            String contents,
            String feedImage,
            Long likeCount,
            LocalDateTime updatedAt
    ) {
        return new GetFeedResponseDto(feedId, title, contents, feedImage, likeCount, updatedAt);
    }
}
