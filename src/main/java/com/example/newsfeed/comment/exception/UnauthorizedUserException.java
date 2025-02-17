package com.example.newsfeed.comment.exception;

import java.util.List;

public class UnauthorizedUserException extends RuntimeException {

    public UnauthorizedUserException(List<ErrorDetail> errorDetail) {
        super(errorDetail.toString());
    }
}
