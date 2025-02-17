package com.example.newsfeed.global.common.exception;

import com.example.newsfeed.global.response.Response;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.newsfeed.global.common.exception.ErrorCode.SERVER_NOT_WORK;
import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice(basePackages = "com.example.schedule")
public class GlobalControllerAdvice {

    @ExceptionHandler(value = BaseException.class)
    public Response<BaseException> baseExceptionHandler(
        BaseException be,
        HttpServletResponse response
    ) {
        List<ErrorDetail> errorDetails = be.getErrorDetails();
        Set<HttpStatus> uniqueStatuses = errorDetails.stream()
            .map(detail -> detail.getErrorCode().getHttpStatus())
            .collect(Collectors.toSet());

        // 고유 상태 코드가 하나만 존재하면 해당 코드, 그렇지 않으면 400(BAD_REQUEST) 사용
        HttpStatus finalStatus = uniqueStatuses.size() == 1
            ? uniqueStatuses.iterator().next()
            : BAD_REQUEST;

        response.setStatus(finalStatus.value());
        return Response.fail(finalStatus, errorDetails);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Response<MethodArgumentNotValidException> methodArgumentNotValidExceptionHandler(
        MethodArgumentNotValidException manve,
        HttpServletResponse response
    ) {
        List<FieldError> fieldErrors = manve.getBindingResult().getFieldErrors();
        List<ErrorDetail> errorDetails = fieldErrors.stream()
            .map(fieldError -> {
                String code = fieldError.getCode();
                String field = fieldError.getField();
                String message = fieldError.getDefaultMessage();
                return new ErrorDetail(code, field, message);
            }).toList();

        response.setStatus(SC_BAD_REQUEST);
        return Response.fail(BAD_REQUEST, errorDetails);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public Response<MethodArgumentNotValidException> methodArgumentTypeMismatchExceptionHandler(
        MethodArgumentTypeMismatchException matme,
        HttpServletResponse response
    ) {
        String code = matme.getErrorCode();
        String field = matme.getName();

        String requiredType = (matme.getRequiredType() != null)
            ? matme.getRequiredType().getSimpleName()
            : "Unknown";

        String message = String.format(
            "필드 \"%s\"에 잘못된 타입이 전달되었습니다. (기대: %s, 전달값: %s)",
            field, requiredType, matme.getValue());

        ErrorDetail errorDetail = new ErrorDetail(code, field, message);
        response.setStatus(SC_BAD_REQUEST);
        return Response.fail(BAD_REQUEST, List.of(errorDetail));
    }

    @ExceptionHandler(Exception.class)
    public Response<Exception> exceptionHandler(
        Exception e,
        HttpServletResponse response
    ) {
        log.error("[Exception]: {}", e.getLocalizedMessage());
        response.setStatus(SC_INTERNAL_SERVER_ERROR);
        return Response.fail(INTERNAL_SERVER_ERROR, List.of(
            new ErrorDetail(SERVER_NOT_WORK, null, "서버 문제로 인해 실패했습니다.")
        ));
    }
}
