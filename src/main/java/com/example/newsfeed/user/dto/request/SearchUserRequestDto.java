package com.example.newsfeed.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SearchUserRequestDto {

    @NotBlank
    @Size(min = 2, message = "검색어는 두 글자보다 짧을 수 없습니다.")
    private final String keyword;
}
