package com.example.newsfeed.user.application.converter;

import com.example.newsfeed.user.dto.response.GetAllUsersResponseDto;
import com.example.newsfeed.user.dto.response.GetUserResponseDto;
import com.example.newsfeed.user.entity.User;
import org.springframework.data.domain.Page;

public class UserConverter {

    private UserConverter() {
    }

    public static GetUserResponseDto toResponse(User user) {
        return GetUserResponseDto.of(user);
    }

    public static Page<GetAllUsersResponseDto> toResponse(Page<User> users) {
        return users.map(GetAllUsersResponseDto::of);
    }
}
