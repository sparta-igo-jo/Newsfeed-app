package com.example.newsfeed.comment.exception;

import java.util.List;

public class CommentNotFoundException extends RuntimeException {

    public CommentNotFoundException(List<ErrorDetail> errorDetail) {
        super(errorDetail.toString());
    }
}
