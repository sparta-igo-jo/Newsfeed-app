package com.example.newsfeed.follow.exception;

import com.example.newsfeed.global.common.exception.BaseException;
import com.example.newsfeed.global.common.exception.ErrorDetail;

import java.util.List;

public class CannotFollowSelfException extends BaseException {
    public CannotFollowSelfException(List<ErrorDetail> errorDetails) {
        super(errorDetails);
    }
}
