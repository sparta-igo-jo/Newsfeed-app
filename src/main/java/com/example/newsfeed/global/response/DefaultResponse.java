package com.example.newsfeed.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class DefaultResponse<T> implements Response<T> {
    private final HttpStatus status;
    private final T data;
    private final LocalDateTime timestamp;
}
