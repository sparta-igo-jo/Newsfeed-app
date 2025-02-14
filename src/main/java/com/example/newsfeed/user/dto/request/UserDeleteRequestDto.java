package com.example.newsfeed.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserDeleteRequestDto {

    @NotBlank
    private String password;
}
