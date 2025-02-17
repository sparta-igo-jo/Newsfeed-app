package com.example.newsfeed.feed.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import javax.xml.stream.events.Comment;
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

    public static GetAllFeedsResponseDto of(
            Long feedId,
            String title,
            String contents,
            String feedImage,
            Long likeCount,
            LocalDateTime updatedAt
    ) {
        return new GetAllFeedsResponseDto(feedId, title, contents, feedImage, likeCount, updatedAt);
    }
}
