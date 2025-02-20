package com.example.newsfeed.common.response;

import com.example.newsfeed.common.exception.ErrorDetail;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED;

@Getter
@AllArgsConstructor
public class ErrorResponse<T> implements Response<T> {

    private final HttpStatus status;

    @JsonFormat(with = WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)
    private final List<ErrorDetail> errorDetails;

    private final LocalDateTime timestamp;

    @Override
    public T getData() {
        return null;  // 에러 응답은 데이터가 없으므로 null
    }
}