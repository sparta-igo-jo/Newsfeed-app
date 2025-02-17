package com.example.newsfeed.auth.application.converter;

import com.example.newsfeed.auth.dto.response.LoginUserResponseDto;
import com.example.newsfeed.auth.dto.response.SignUpUserResponseDto;
import com.example.newsfeed.user.entity.User;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthConverter {

    public static SignUpUserResponseDto toSignUpResponse(User user) {
        return SignUpUserResponseDto.of(
                user.getId(),
                user.getEmail(),
                user.getName()
        );
    }

    public static LoginUserResponseDto toLoginResponse(User user) {
        return LoginUserResponseDto.of(
                user.getId(),
                user.getEmail(),
                user.getName()
        );
    }
}
