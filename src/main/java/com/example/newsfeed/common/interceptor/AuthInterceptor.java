package com.example.newsfeed.common.interceptor;

import com.example.newsfeed.common.exception.BaseException;
import com.example.newsfeed.common.exception.ErrorDetail;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

import static com.example.newsfeed.common.constant.SessionConst.LOGIN_USER;
import static com.example.newsfeed.common.exception.ErrorCode.USER_NOT_LOGIN;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler
    ) {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(LOGIN_USER) == null) {
            throw new BaseException(List.of(
                new ErrorDetail(USER_NOT_LOGIN, null, USER_NOT_LOGIN.getMessage())
            ));
        }
        return true;
    }
}
