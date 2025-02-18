package com.example.newsfeed.global.common.file.exception;

import com.example.newsfeed.global.common.exception.BaseException;
import com.example.newsfeed.global.common.exception.ErrorDetail;

import java.util.List;

public class FileUploadFailedException extends BaseException {

    public FileUploadFailedException(List<ErrorDetail> errorDetails) {
        super(errorDetails);
    }
}
