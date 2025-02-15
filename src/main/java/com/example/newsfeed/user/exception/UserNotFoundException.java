package com.example.newsfeed.user.exception;

import com.example.newsfeed.global.common.exception.BaseException;
import com.example.newsfeed.global.common.exception.ErrorDetail;

import java.util.List;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(List<ErrorDetail> errorDetails) {
        super(errorDetails);
    }
}
