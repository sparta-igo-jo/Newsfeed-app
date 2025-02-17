package com.example.newsfeed.global.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_EMAIL_DUPLICATION("UserEmailDuplication", "다른 유저와 이메일이 중복됩니다."),
    USER_NAME_DUPLICATION("UserNameDuplication", "다른 유저와 이름이 중복됩니다."),
    USER_NOT_LOGIN("UserNotLogin", "로그인이 필요합니다. 로그인을 해주세요."),
    USER_NOT_FOUND("UserNotFound", "해당하는 유저를 찾을 수 없습니다."),
    INVALID_PASSWORD("InvalidPassword", "패스워드가 올바르지 않습니다."),
    UPLOAD_FAILED("UploadFailed", "파일 업로드에 실패했습니다."),
    FILE_NOT_FOUND("FileNotFound", "파일을 찾을 수 없습니다."),
    PASSWORD_SAME_AS_OLD("PasswordSameAsOld", "이전 패스워드와 동일할 수 없습니다."),
 feature/comments

    /**
     * 댓글용 에러 코드
     */
    FEED_NOT_FOUND("FeedNotFound", "해당하는 게시글을 찾을 수 없습니다."),
    COMMENT_NOT_FOUND("CommentNotFound", "해당하는 댓글을 찾을 수 없습니다."),
    UNAUTHORIZED("Unauthorized", "권한이 없습니다.");


 develop

    private final String code;
    private final String message;
}
