package com.example.newsfeed.feed.application.converter;

import com.example.newsfeed.feed.dto.response.CreateFeedResponseDto;
import com.example.newsfeed.feed.dto.response.GetAllFeedsResponseDto;
import com.example.newsfeed.feed.dto.response.GetFeedResponseDto;
import com.example.newsfeed.feed.entity.Feed;
import org.springframework.data.domain.Page;

import javax.xml.stream.events.Comment;

public class FeedConverter {

    private FeedConverter(){
    }

    public static CreateFeedResponseDto toResponse(Feed feed) {
        return CreateFeedResponseDto.of(
                feed.getId(),
                feed.getTitle(),
                feed.getContents(),
                feed.getFeedImage(),
                feed.getLikeCount(),
                feed.getUpdatedAt()
        );
    }

    public static GetFeedResponseDto toResponse(Feed feed, Page<Comment> comments) {
        return GetFeedResponseDto.of(
                feed.getId(),
                feed.getTitle(),
                feed.getContents(),
                feed.getFeedImage(),
                feed.getLikeCount(),
                feed.getUpdatedAt(),
                comments
        );
    }

    public static Page<GetAllFeedsResponseDto> toResponse(Page<Feed> feeds) {
        return feeds.map(feed -> GetAllFeedsResponseDto.of(
                feed.getId(),
                feed.getTitle(),
                feed.getContents(),
                feed.getFeedImage(),
                feed.getLikeCount(),
                feed.getUpdatedAt()
            ));
    }
}
