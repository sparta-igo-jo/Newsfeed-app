package com.example.newsfeed.feed.exception;

import com.example.newsfeed.global.common.exception.BaseException;
import com.example.newsfeed.global.common.exception.ErrorDetail;

import java.util.List;

public class FeedNotFoundException extends BaseException {

    public FeedNotFoundException(List<ErrorDetail> errorDetails) {
        super(errorDetails);
    }
}
