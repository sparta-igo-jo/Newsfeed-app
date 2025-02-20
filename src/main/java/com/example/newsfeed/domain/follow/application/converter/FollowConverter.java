package com.example.newsfeed.domain.follow.application.converter;

import com.example.newsfeed.domain.follow.dto.response.GetFollowResponseDto;
import com.example.newsfeed.domain.user.entity.User;

public class FollowConverter {

    private FollowConverter() {
    }

    public static GetFollowResponseDto toResponse(User user, boolean isFollowing) {
        return GetFollowResponseDto.of(user, isFollowing);
    }
}
