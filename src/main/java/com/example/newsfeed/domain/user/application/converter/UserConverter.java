package com.example.newsfeed.domain.user.application.converter;

import com.example.newsfeed.domain.user.dto.response.GetAllUsersResponseDto;
import com.example.newsfeed.domain.user.dto.response.GetUserResponseDto;
import com.example.newsfeed.domain.user.entity.User;
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
