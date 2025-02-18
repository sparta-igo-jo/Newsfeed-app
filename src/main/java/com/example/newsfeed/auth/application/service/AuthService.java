package com.example.newsfeed.auth.application.service;

import com.example.newsfeed.auth.application.converter.AuthConverter;
import com.example.newsfeed.auth.dto.request.LoginUserRequestDto;
import com.example.newsfeed.auth.dto.request.SignUpUserRequestDto;
import com.example.newsfeed.auth.dto.response.LoginUserResponseDto;
import com.example.newsfeed.auth.dto.response.SignUpUserResponseDto;
import com.example.newsfeed.auth.repository.AuthRepository;
import com.example.newsfeed.global.common.exception.BaseException;
import com.example.newsfeed.global.common.exception.ErrorDetail;
import com.example.newsfeed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.newsfeed.global.common.constant.ImageUrlConst.DEFAULT_PROFILE_IMAGE_PATH;
import static com.example.newsfeed.global.common.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignUpUserResponseDto signUpUser(SignUpUserRequestDto dto) {
        isUserEmailDuplication(dto.getEmail());
        isUserNameDuplication(dto.getName());

        User createUser = authRepository.save(
            User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .profileImage(DEFAULT_PROFILE_IMAGE_PATH)
                .build()
        );

        return AuthConverter.toSignUpResponse(createUser);
    }

    @Transactional(readOnly = true)
    public LoginUserResponseDto loginUser(LoginUserRequestDto dto) {
        User findUser = authRepository.findUserByEmailAndDeletedAtIsNull(dto.getEmail())
            .orElseThrow(() -> new BaseException(List.of(
                new ErrorDetail(USER_NOT_FOUND, null, USER_NOT_FOUND.getMessage())
            )));

        if (!passwordEncoder.matches(dto.getPassword(), findUser.getPassword())) {
            throw new BaseException(List.of(
                new ErrorDetail(INVALID_PASSWORD, null, INVALID_PASSWORD.getMessage())
            ));
        }

        return AuthConverter.toLoginResponse(findUser);
    }

    // 유저 이메일 중복 검사 메서드
    private void isUserEmailDuplication(String email) {
        if (authRepository.existsByEmail(email)) {
            throw new BaseException(List.of(
                new ErrorDetail(
                    USER_EMAIL_DUPLICATION, "email", USER_EMAIL_DUPLICATION.getMessage()
                )
            ));
        }
    }

    // 유저 이름 중복 검사 메서드
    private void isUserNameDuplication(String name) {
        if (authRepository.existsByName(name)) {
            throw new BaseException(List.of(
                new ErrorDetail(
                    USER_NAME_DUPLICATION, "name", USER_NAME_DUPLICATION.getMessage()
                )
            ));
        }
    }
}
