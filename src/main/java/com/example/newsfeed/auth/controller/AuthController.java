package com.example.newsfeed.auth.controller;

import com.example.newsfeed.auth.application.service.AuthService;
import com.example.newsfeed.auth.dto.request.LoginUserRequestDto;
import com.example.newsfeed.auth.dto.response.LoginUserResponseDto;
import com.example.newsfeed.auth.dto.response.SignUpUserResponseDto;
import com.example.newsfeed.global.response.Response;
import com.example.newsfeed.auth.dto.request.SignUpUserRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.example.newsfeed.global.common.constant.SessionConst.LOGIN_USER;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public Response<SignUpUserResponseDto> signUpUser(@Valid @RequestBody SignUpUserRequestDto dto) {
        SignUpUserResponseDto createUserDto = authService.signUpUser(dto);
        return Response.of(createUserDto, "회원가입 되었습니다.");
    }

    @PostMapping("/login")
    public Response<LoginUserResponseDto> loginUser(
            @Valid @RequestBody LoginUserRequestDto dto,
            HttpServletRequest request
    ) {
        LoginUserResponseDto loginUserDto = authService.loginUser(dto);

        HttpSession session = request.getSession();
        session.setAttribute(LOGIN_USER, loginUserDto.getId());

        return Response.of(loginUserDto, "로그인 되었습니다.");
    }

    @PostMapping("/logout")
    public Response<Void> logoutUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if(session != null) session.invalidate();

        return Response.of(null, "로그아웃 되었습니다.");
    }
}