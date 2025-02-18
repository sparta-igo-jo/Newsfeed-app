package com.example.newsfeed.feed.dto.response;

import com.example.newsfeed.feed.entity.Feed;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import javax.xml.stream.events.Comment;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class GetFeedResponseDto {

    private final Long feedId;

    private final String title;

    private final String contents;

    private final String feedImage;

    private final Long likeCount;

    private final LocalDateTime updatedAt;

    public static GetFeedResponseDto of(Feed feed) {
        return new GetFeedResponseDto(
                feed.getId(),
                feed.getTitle(),
                feed.getContents(),
                feed.getFeedImage(),
                feed.getLikeCount(),
                feed.getUpdatedAt()
        );
    }
}
