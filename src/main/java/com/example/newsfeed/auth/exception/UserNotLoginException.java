package com.example.newsfeed.auth.exception;

import com.example.newsfeed.global.common.exception.BaseException;
import com.example.newsfeed.global.common.exception.ErrorDetail;

import java.util.List;

public class UserNotLoginException extends BaseException {

    public UserNotLoginException(List<ErrorDetail> errorDetails) { super(errorDetails); }
}
