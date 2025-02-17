package com.example.newsfeed.auth.controller;

import com.example.newsfeed.auth.application.service.AuthService;
import com.example.newsfeed.auth.dto.request.LoginUserRequestDto;
import com.example.newsfeed.auth.dto.response.LoginUserResponseDto;
import com.example.newsfeed.auth.dto.response.SignUpUserResponseDto;
import com.example.newsfeed.global.common.constant.SessionConst;
import com.example.newsfeed.global.response.Response;
import com.example.newsfeed.auth.dto.request.SignUpUserRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public Response<SignUpUserResponseDto> signUpUser(@Valid @RequestBody SignUpUserRequestDto dto) {
        SignUpUserResponseDto createUserDto = authService.signUpUser(dto);
        return Response.of(createUserDto);
    }

    @PostMapping("/login")
    public Response<LoginUserResponseDto> loginUser(
            @Valid @RequestBody LoginUserRequestDto dto,
            HttpServletRequest request
    ) {
        LoginUserResponseDto loginUserDto = authService.loginUser(dto);

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_USER, loginUserDto.getId());

        return Response.of(loginUserDto);
    }

    @PostMapping("/logout")
    public Response<String> logoutUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if(session != null) session.invalidate();

        return Response.of("로그아웃 성공!");
    }
}