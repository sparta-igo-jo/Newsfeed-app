package com.example.newsfeed.user.application.converter;

import com.example.newsfeed.user.dto.response.GetAllUsersResponseDto;
import com.example.newsfeed.user.dto.response.GetUserResponseDto;
import com.example.newsfeed.user.entity.User;

import java.util.List;

public class UserConverter {

    private UserConverter() {
    }

    public static GetUserResponseDto toResponse(User user) {
        return GetUserResponseDto.of(
            user.getId(),
            user.getName(),
            user.getProfileImage(),
            user.getDescription(),
            user.getFollowersCount(),
            user.getFollowingsCount()
        );
    }

    public static List<GetAllUsersResponseDto> toResponse(List<User> users) {
        return users.stream()
            .map(user -> GetAllUsersResponseDto.of(
                user.getId(),
                user.getName(),
                user.getProfileImage(),
                user.getDescription()
            ))
            .toList();
    }
}
