package com.example.newsfeed.comment.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class BaseException extends RuntimeException {

    private final List<ErrorDetail> errorDetails;

    public BaseException(ErrorCode errorCode, String field) {
        this.errorDetails = List.of(new ErrorDetail(errorCode, field, errorCode.getMessage()));
    }

    public BaseException(List<ErrorDetail> errorDetails) {
        this.errorDetails = errorDetails;
    }
}
