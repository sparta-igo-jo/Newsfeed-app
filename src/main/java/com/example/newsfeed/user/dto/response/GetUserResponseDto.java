package com.example.newsfeed.user.dto.response;

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

    public static GetUserResponseDto of(
        Long userId,
        String name,
        String profileImage,
        String description,
        Long followersCount,
        Long followingsCount
    ) {
        return new GetUserResponseDto(userId, name, profileImage, description, followersCount, followingsCount);
    }

}
