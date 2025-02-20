package com.example.newsfeed.domain.feed.dto.response;

import com.example.newsfeed.domain.feed.entity.Feed;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class GetAllFeedsResponseDto {

    private final Long feedId;

    private final String title;

    private final String contents;

    private final String feedImage;

    private final Long likeCount;

    private final LocalDateTime updatedAt;

    public static GetAllFeedsResponseDto of(Feed feed) {
        return new GetAllFeedsResponseDto(
                feed.getId(),
                feed.getTitle(),
                feed.getContents(),
                feed.getFeedImage(),
                feed.getLikeCount(),
                feed.getUpdatedAt()
        );
    }
}
