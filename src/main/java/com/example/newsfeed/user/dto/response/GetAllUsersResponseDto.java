package com.example.newsfeed.user.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetAllUsersResponseDto {

    private final Long userId;

    private final String name;

    private final String profileImage;

    private final String description;

    public static GetAllUsersResponseDto of(
        Long userId,
        String name,
        String profileImage,
        String description
    ) {
        return new GetAllUsersResponseDto(userId, name, profileImage, description);
    }
}
