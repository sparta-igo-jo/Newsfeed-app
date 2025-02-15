package com.example.newsfeed.user.application.service;

import com.example.newsfeed.user.dto.response.GetAllUsersResponseDto;
import com.example.newsfeed.user.dto.response.GetUserResponseDto;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserRepository userRepository;

    @Test
    public void 유저_단건_조회() {
        // given
        Long searchId = 1L;
        String searchName = "tester";

        User newUser = mock(User.class);
        when(userRepository.findUserById(searchId)).thenReturn(Optional.of(newUser));
        when(newUser.getId()).thenReturn(searchId);
        when(newUser.getName()).thenReturn(searchName);

        // when
        GetUserResponseDto response = userService.findUser(searchId);

        // then
        assertThat(response.getUserId()).isEqualTo(searchId);
        assertThat(response.getName()).isEqualTo(searchName);
    }

    @Test
    public void 유저_전체_조회() {
        // given
        Long searchId1 = 1L;
        Long searchId2 = 2L;
        String searchName1 = "tester1";
        String searchName2 = "tester2";

        User newUser1 = mock(User.class);
        when(newUser1.getId()).thenReturn(searchId1);
        when(newUser1.getName()).thenReturn(searchName1);

        User newUser2 = mock(User.class);
        when(newUser2.getId()).thenReturn(searchId2);
        when(newUser2.getName()).thenReturn(searchName2);

        when(userRepository.findAllUsers()).thenReturn(List.of(newUser1, newUser2));

        // when
        List<GetAllUsersResponseDto> responses = userService.findUsers();

        // then
        assertThat(responses.size()).isEqualTo(2);

        assertThat(responses.get(0).getUserId()).isEqualTo(searchId1);
        assertThat(responses.get(0).getName()).isEqualTo(searchName1);

        assertThat(responses.get(1).getUserId()).isEqualTo(searchId2);
        assertThat(responses.get(1).getName()).isEqualTo(searchName2);
    }
}