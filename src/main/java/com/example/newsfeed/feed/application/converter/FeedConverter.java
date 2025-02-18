package com.example.newsfeed.feed.application.converter;

import com.example.newsfeed.feed.dto.response.GetAllFeedsResponseDto;
import com.example.newsfeed.feed.dto.response.GetFeedResponseDto;
import com.example.newsfeed.feed.entity.Feed;
import org.springframework.data.domain.Page;

import javax.xml.stream.events.Comment;

public class FeedConverter {

    private FeedConverter(){
    }

    public static GetFeedResponseDto toResponse(Feed feed) {
        return GetFeedResponseDto.of(feed);
    }

    public static Page<GetAllFeedsResponseDto> toResponse(Page<Feed> feeds) {
        return feeds.map(feed -> GetAllFeedsResponseDto.of(feeds));
    }
}
