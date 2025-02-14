package com.example.newsfeed.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface Response<T> {
    T getData();

    static <T> Response<T> of(T data) {
        return new DefaultResponse<>(OK, data, LocalDateTime.now());
    }

    static <T> Response<T> empty() {
        return new DefaultResponse<>(OK, null, LocalDateTime.now());
    }

}
