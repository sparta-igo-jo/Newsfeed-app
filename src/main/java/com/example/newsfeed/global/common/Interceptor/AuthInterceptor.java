package com.example.newsfeed.global.common.Interceptor;

import com.example.newsfeed.auth.exception.UserNotLoginException;
import com.example.newsfeed.global.common.Const.SessionConst;
import com.example.newsfeed.global.common.exception.ErrorDetail;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

import static com.example.newsfeed.global.common.exception.ErrorCode.USER_NOT_LOGIN;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler
    ) {
        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute(SessionConst.LOGIN_USER) == null) {
            // TODO : GlobalExceptionHandler 에서 예외처리 필요
            throw new UserNotLoginException(List.of(
                    new ErrorDetail(USER_NOT_LOGIN, null, USER_NOT_LOGIN.getMessage())
            ));
        }

        return true;
    }
}
