package com.example.newsfeed.domain.feed.application.converter;

import com.example.newsfeed.domain.feed.dto.response.GetAllFeedsResponseDto;
import com.example.newsfeed.domain.feed.dto.response.GetFeedResponseDto;
import com.example.newsfeed.domain.feed.entity.Feed;
import org.springframework.data.domain.Page;

public class FeedConverter {

    private FeedConverter(){
    }

    public static GetFeedResponseDto toResponse(Feed feed) {
        return GetFeedResponseDto.of(feed);
    }

    public static Page<GetAllFeedsResponseDto> toResponse(Page<Feed> feeds) {
        return feeds.map(GetAllFeedsResponseDto::of);
    }
}
