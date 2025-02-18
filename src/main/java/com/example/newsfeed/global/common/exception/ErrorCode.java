package com.example.newsfeed.global.common.exception;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {



    // 유저 관련 예외 코드
    USER_EMAIL_DUPLICATION("다른 유저와 이메일이 중복됩니다.", CONFLICT),
    USER_NAME_DUPLICATION("다른 유저와 이름이 중복됩니다.", CONFLICT),
    USER_NOT_LOGIN("로그인이 필요합니다. 로그인을 해주세요.", UNAUTHORIZED),
    USER_NOT_FOUND("해당하는 유저를 찾을 수 없습니다.", NOT_FOUND),
    INVALID_PASSWORD("패스워드가 올바르지 않습니다.", BAD_REQUEST),
    PASSWORD_SAME_AS_OLD("이전 패스워드와 동일할 수 없습니다.", BAD_REQUEST),
    USER_ACCESS_DENIED("사용자가 접근할 수 있는 권한이 없습니다.", FORBIDDEN),


    // 피드 관련 예외 코드
    FEED_NOT_FOUND("해당 피드를 찾을 수 없습니다.", NOT_FOUND),
    FEED_ACCESS_DENIED("피드에 접근할 권한이 없습니다.", FORBIDDEN),


    // 댓글 관련 예외 코드
    COMMENT_NOT_FOUND("해당 댓글을 찾을 수 없습니다.", NOT_FOUND),
    COMMENT_ACCESS_DENIED("댓글에 접근할 권한이 없습니다.", FORBIDDEN),

    // 파일 관련 예외 코드
    UPLOAD_FAILED("파일 업로드에 실패했습니다.", INTERNAL_SERVER_ERROR),
    UN_SUPPORTED_FILE_TYPE("지원하지 않는 파일 타입입니다.", BAD_REQUEST),
    FILE_NOT_FOUND("파일을 찾을 수 없습니다.", NOT_FOUND),


    // 좋아요 관련 예외 코드


    // 팔로우 관련 예외 코드
    CANNOT_FOLLOW_SELF("자기 자신을 팔로우할 수 없습니다.", BAD_REQUEST),


    // 서버 문제로 인한 예외
    SERVER_NOT_WORK("서버 문제로 인해 실패했습니다.", INTERNAL_SERVER_ERROR);



    private final String message;
    private final HttpStatus httpStatus;

    public void apply(HttpServletResponse response) {
        response.setStatus(httpStatus.value());
    }
}
