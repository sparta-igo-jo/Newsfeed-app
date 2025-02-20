package com.example.newsfeed.domain.auth.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginUserResponseDto {
    private final Long id;
    private final String email;
    private final String name;

    public static LoginUserResponseDto of(
            Long id,
            String email,
            String name
    ) {
        return new LoginUserResponseDto(id, email, name);
    }
}
