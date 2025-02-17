package com.example.newsfeed.user.dto.response;

import com.example.newsfeed.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetUserResponseDto {

    private final Long userId;

    private final String name;

    private final String profileImage;

    private final String description;

    private final Long followersCount;

    private final Long followingsCount;

    public static GetUserResponseDto of(User user) {
        return new GetUserResponseDto(
            user.getId(),
            user.getName(),
            user.getProfileImage(),
            user.getDescription(),
            user.getFollowersCount(),
            user.getFollowingsCount()
        );
    }

}
