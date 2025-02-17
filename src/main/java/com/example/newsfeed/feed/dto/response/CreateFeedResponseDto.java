package com.example.newsfeed.feed.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CreateFeedResponseDto {

    private final Long feedId;

    public static CreateFeedResponseDto of(
            Long feedId
    ) {
        return new CreateFeedResponseDto(feedId, title, contents, feedImage, likeCount, updatedAt);
    }
}
