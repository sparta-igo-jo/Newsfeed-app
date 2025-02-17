package com.example.newsfeed.global.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND("UserNotFound", "해당하는 유저를 찾을 수 없습니다."),
    INVALID_PASSWORD("InvalidPassword", "패스워드가 올바르지 않습니다."),
    UPLOAD_FAILED("UploadFailed", "파일 업로드에 실패했습니다."),
    FILE_NOT_FOUND("FileNotFound", "파일을 찾을 수 없습니다."),
    PASSWORD_SAME_AS_OLD("PasswordSameAsOld", "이전 패스워드와 동일할 수 없습니다."),

    CANNOT_FOLLOW_SELF("CannotFollowSelf","자기 자신을 팔로우할 수 없습니다.");

    private final String code;
    private final String message;
}
