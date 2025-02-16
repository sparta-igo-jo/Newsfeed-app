package com.example.newsfeed.feed.application.converter;

import com.example.newsfeed.feed.dto.response.GetFeedResponseDto;
import com.example.newsfeed.feed.entity.Feed;
import org.springframework.data.domain.Page;

public class FeedConverter {

    private FeedConverter(){
    }

    public static GetFeedResponseDto toResponse(Feed feed) {
        return GetFeedResponseDto.of(
                feed.getId(),
                feed.getTitle(),
                feed.getContents(),
                feed.getFeedImage(),
                feed.getLikeCount(),
                feed.getUpdatedAt()
        );
    }

    public static Page<GetFeedResponseDto> toResponse(Page<Feed> feeds) {
        return feeds.map(feed -> GetFeedResponseDto.of(
                feed.getId(),
                feed.getTitle(),
                feed.getContents(),
                feed.getFeedImage(),
                feed.getLikeCount(),
                feed.getUpdatedAt()
            ));
    }
}
