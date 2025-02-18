package com.example.newsfeed.like.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeResponseDto {
    private Long feedId;
    private boolean liked;
    private Long likeCount;
}
