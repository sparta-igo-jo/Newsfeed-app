package com.example.newsfeed.domain.auth.application.converter;

import com.example.newsfeed.domain.auth.dto.response.LoginUserResponseDto;
import com.example.newsfeed.domain.auth.dto.response.SignUpUserResponseDto;
import com.example.newsfeed.domain.user.entity.User;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
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
