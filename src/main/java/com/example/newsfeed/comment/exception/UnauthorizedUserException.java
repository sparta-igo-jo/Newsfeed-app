package com.example.newsfeed.comment.exception;

import com.example.newsfeed.global.common.exception.BaseException;
import com.example.newsfeed.global.common.exception.ErrorDetail;

import java.util.List;

public class UnauthorizedUserException extends BaseException {

    public UnauthorizedUserException(List<ErrorDetail> errorDetails) {
        super(errorDetails);
    }
}
