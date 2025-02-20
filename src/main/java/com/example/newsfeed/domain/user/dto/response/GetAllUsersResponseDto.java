package com.example.newsfeed.domain.user.dto.response;

import com.example.newsfeed.domain.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetAllUsersResponseDto {

    private final Long userId;

    private final String name;

    private final String profileImage;

    private final String description;

    public static GetAllUsersResponseDto of(User user) {
        return new GetAllUsersResponseDto(
            user.getId(),
            user.getName(),
            user.getProfileImage(),
            user.getDescription()
        );
    }
}
