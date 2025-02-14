package com.example.newsfeed.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserCreateRequestDto {

    @NotBlank
    @Pattern(
        regexp = "^[a-zA-Z0-9-_]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
        message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @NotBlank
    @Size(min = 2, max = 15, message = "이름은 최소 2자 이상, 최대 15자여야 합니다.")
    private String name;

    @NotBlank
    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$",
        message = "비밀번호는 8자 이상, 최소 하나의 영문자, 숫자, 특수 문자를 포함해야 합니다.")
    private String password;

}
