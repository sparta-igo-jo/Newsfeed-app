package com.example.newsfeed.global.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class DefaultResponse<T> implements Response<T> {

    private final HttpStatus status;
    private final T data;
    private final LocalDateTime timestamp;
    private String message;

    public DefaultResponse(HttpStatus status, T data, LocalDateTime timestamp) {
        this.status = status;
        this.data = data;
        this.timestamp = timestamp;
    }

    public DefaultResponse(HttpStatus status, T data, String message, LocalDateTime timestamp) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.timestamp = timestamp;
    }
}
