package com.example.newsfeed.global.response;

import com.example.newsfeed.global.common.exception.ErrorDetail;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface Response<T> {

    T getData();

    static <T> Response<T> of(T data) {
        return new DefaultResponse<>(OK, data, LocalDateTime.now());
    }

    static <T> Response<T> empty() {
        return new DefaultResponse<>(OK, null, LocalDateTime.now());
    }

    static <T> Response<T> fail(HttpStatus status, List<ErrorDetail> errorDetails) {
        return new ErrorResponse<>(status, errorDetails, LocalDateTime.now());
    }
}
