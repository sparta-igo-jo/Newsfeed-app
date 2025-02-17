package com.example.newsfeed.auth.exception;

import com.example.newsfeed.global.common.exception.BaseException;
import com.example.newsfeed.global.common.exception.ErrorDetail;

import java.util.List;

public class UserNameDuplicationException extends BaseException {

    public UserNameDuplicationException(List<ErrorDetail> errorDetails) {
        super(errorDetails);
    }
}
