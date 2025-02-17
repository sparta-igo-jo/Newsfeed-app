package com.example.newsfeed.feed.exception;

import com.example.newsfeed.global.common.exception.BaseException;
import com.example.newsfeed.global.common.exception.ErrorDetail;

import java.util.List;

public class FeedUpdateFailedException extends BaseException {

    public FeedUpdateFailedException(List<ErrorDetail> errorDetails) {
        super(errorDetails);
    }
}
