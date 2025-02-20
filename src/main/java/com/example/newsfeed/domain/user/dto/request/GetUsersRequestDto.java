package com.example.newsfeed.domain.user.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class GetUsersRequestDto {

    private final List<Long> userIds;
}
