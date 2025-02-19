package com.example.newsfeed.follow.dto.response;

import com.example.newsfeed.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetFollowResponseDto {

    private final Long userId;
    private final String name;
    private final String imagePath;
    private final boolean isFollowing;

    public static GetFollowResponseDto of(User user, boolean isFollowing) {
        return new GetFollowResponseDto(
            user.getId(),
            user.getName(),
            user.getProfileImage(),
            isFollowing
        );
    }
}
