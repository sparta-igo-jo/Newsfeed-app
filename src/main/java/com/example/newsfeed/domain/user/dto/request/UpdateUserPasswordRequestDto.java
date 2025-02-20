package com.example.newsfeed.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.example.newsfeed.common.constant.RegexpConst.PASSWORD_REGEXP_CONST;

@Getter
@RequiredArgsConstructor
public class UpdateUserPasswordRequestDto {

    @NotBlank
    private final String password;

    @NotBlank
    @Pattern(
        regexp = PASSWORD_REGEXP_CONST,
        message = "비밀번호는 8자 이상, 최소 하나의 영문자, 숫자, 특수 문자를 포함해야 합니다.")
    private final String changePassword;
}
