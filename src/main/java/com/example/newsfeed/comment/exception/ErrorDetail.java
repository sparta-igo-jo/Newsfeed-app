package com.example.newsfeed.comment.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorDetail {

    @JsonProperty(value = "code")
    private String code;

    @JsonProperty(value = "code")
    private ErrorCode errorCode;

    private String field;

    private String message;

    public ErrorDetail(String code, String field, String message) {
        this.code = code;
        this.field = field;
        this.message = message;
    }

    public ErrorDetail(ErrorCode errorCode, String field, String message) {
        this.errorCode = errorCode;
        this.field = field;
        this.message = message;
    }

    public ErrorDetail(String field, String message) {
        this.field = field;
        this.message = message;
    }

    // JSON은 직렬화 시 접근 수준에 따라 접근 순서가 정해짐
    // 따라서 아래의 Getter에 먼저 접근해서 code 값에 따라 String/ErrorCode 둘 중 하나의 값을 사용
    @JsonProperty("code")
    public String getSerializedCode() {
        return code != null
            ? code : (errorCode != null ? errorCode.name() : null);
    }
}