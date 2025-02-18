package com.example.newsfeed.comment.exception;

import com.example.newsfeed.global.common.exception.BaseException;
import com.example.newsfeed.global.common.exception.ErrorDetail;

import java.util.List;

public class CommentNotFoundException extends BaseException {

    public CommentNotFoundException(List<ErrorDetail> errorDetails) {
        super(errorDetails);
    }
}
