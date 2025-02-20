package com.example.newsfeed.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.example.newsfeed.common.constant.RegexpConst.EMAIL_REGEXP_CONST;
import static com.example.newsfeed.common.constant.RegexpConst.PASSWORD_REGEXP_CONST;

@Getter
@RequiredArgsConstructor
public class SignUpUserRequestDto {

    @NotBlank
    @Pattern(
        regexp = EMAIL_REGEXP_CONST,
        message = "이메일 형식에 맞지 않습니다.")
    private final String email;

    @NotBlank
    @Size(
        min = 2, max = 15,
        message = "이름은 최소 2자 이상, 최대 15자여야 합니다.")
    private final String name;

    @NotBlank
    @Pattern(
        regexp = PASSWORD_REGEXP_CONST,
        message = "비밀번호는 8자 이상, 최소 하나의 영문자, 숫자, 특수 문자를 포함해야 합니다.")
    private final String password;

}
