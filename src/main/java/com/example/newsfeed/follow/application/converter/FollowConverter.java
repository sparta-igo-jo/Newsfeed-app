package com.example.newsfeed.follow.application.converter;

import com.example.newsfeed.follow.dto.response.GetFollowResponseDto;
import com.example.newsfeed.user.entity.User;

public class FollowConverter {

    private FollowConverter() {
    }

    public static GetFollowResponseDto toResponse(User user, boolean isFollowing) {
        return GetFollowResponseDto.of(user, isFollowing);
    }
}
