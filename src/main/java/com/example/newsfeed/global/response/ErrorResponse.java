package com.example.newsfeed.global.response;

import com.example.newsfeed.global.common.exception.ErrorDetail;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private final HttpStatus status;

    @JsonFormat(with = WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)
    private final List<ErrorDetail> errorDetails;

    private final LocalDateTime timestamp;

    public static ErrorResponse fail(HttpStatus status, List<ErrorDetail> errorDetails) {
        return new ErrorResponse(status, errorDetails, LocalDateTime.now());
    }
}