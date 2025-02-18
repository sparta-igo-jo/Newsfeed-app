package com.example.newsfeed.user.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@RequiredArgsConstructor
public class UpdateUserRequestDto {

    @Nullable
    @JsonInclude(NON_NULL)
    @Size(min = 2, max = 15, message = "이름은 최소 2자 이상, 최대 15자여야 합니다.")
    private final String name;

    @Nullable
    @JsonInclude(NON_NULL)
    private final String imagePath;

    @Nullable
    @JsonInclude(NON_NULL)
    @Size(max = 255, message = "소개글은 최대 255자여야 합니다.")
    private final String description;

    @NotBlank
    private final String password;
}
