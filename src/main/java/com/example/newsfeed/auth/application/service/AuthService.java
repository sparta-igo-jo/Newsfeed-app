package com.example.newsfeed.auth.application.service;

import com.example.newsfeed.auth.application.converter.AuthConverter;
import com.example.newsfeed.auth.dto.request.LoginUserRequestDto;
import com.example.newsfeed.auth.dto.response.LoginUserResponseDto;
import com.example.newsfeed.auth.dto.response.SignUpUserResponseDto;
import com.example.newsfeed.auth.exception.UserEmailDuplicationExcepion;
import com.example.newsfeed.auth.exception.UserNameDuplicationException;
import com.example.newsfeed.auth.repository.AuthRepository;
import com.example.newsfeed.global.common.exception.ErrorDetail;
import com.example.newsfeed.user.dto.request.CreateUserRequestDto;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.exception.InvalidPasswordException;
import com.example.newsfeed.user.exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.newsfeed.global.common.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignUpUserResponseDto signUpUser(CreateUserRequestDto dto) {
        userEmailDuplication(dto.getEmail());
        userNameDuplication(dto.getName());

        User createUser = authRepository.save(
                new User(
                    dto.getEmail(),
                    passwordEncoder.encode(dto.getPassword()),
                    dto.getName()
                )
        );

        return AuthConverter.toSignUpResponse(createUser);
    }

    public LoginUserResponseDto loginUser(LoginUserRequestDto dto) {
        User findUser = authRepository.findByEmail(dto.getEmail())
                .orElseThrow(() ->
                        new UserNotFoundException(List.of(
                                new ErrorDetail(
                                        USER_NOT_FOUND, null, USER_NOT_FOUND.getMessage()
                                )
                        ))
                );

        if(!passwordEncoder.matches(dto.getPassword(), findUser.getPassword())) {
            throw new InvalidPasswordException(List.of(
                    new ErrorDetail(INVALID_PASSWORD, null, INVALID_PASSWORD.getMessage())
            ));
        }

        return AuthConverter.toLoginResponse(findUser);
    }

    public void logoutUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if(session != null) session.invalidate();
    }

    // 유저 이메일 중복 검사 메서드
    private void userEmailDuplication(String email) {
        if(authRepository.existsByEmail(email)) {
            throw new UserEmailDuplicationExcepion(List.of(
                    new ErrorDetail(
                            USER_EMAIL_DUPLICATION, "email", USER_EMAIL_DUPLICATION.getMessage()
                    )
            ));
        }
    }

    // 유저 이름 중복 검사 메서드
    private void userNameDuplication(String name) {
        if(authRepository.existsByName(name)) {
            throw new UserNameDuplicationException(List.of(
               new ErrorDetail(
                       USER_NAME_DUPLICATION, "name", USER_NAME_DUPLICATION.getMessage()
               )
            ));
        }
    }
}
