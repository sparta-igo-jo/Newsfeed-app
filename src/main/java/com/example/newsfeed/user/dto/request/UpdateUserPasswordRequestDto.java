package com.example.newsfeed.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateUserPasswordRequestDto {

    @NotBlank
    private final String password;

    @NotBlank
    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$",
        message = "비밀번호는 8자 이상, 최소 하나의 영문자, 숫자, 특수 문자를 포함해야 합니다.")
    private final String changePassword;
}
