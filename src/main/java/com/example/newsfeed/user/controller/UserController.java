package com.example.newsfeed.user.controller;

import com.example.newsfeed.global.response.Response;
import com.example.newsfeed.user.application.service.UserService;
import com.example.newsfeed.user.dto.request.DeleteUserRequestDto;
import com.example.newsfeed.user.dto.request.UpdateUserRequestDto;
import com.example.newsfeed.user.dto.response.GetAllUsersResponseDto;
import com.example.newsfeed.user.dto.response.GetUserResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public Response<GetUserResponseDto> getUser(@PathVariable Long userId) {
        GetUserResponseDto getUserDto = userService.findUser(userId);
        return Response.of(getUserDto);
    }

    @GetMapping
    public Response<List<GetAllUsersResponseDto>> getUser() {
        List<GetAllUsersResponseDto> getUsersDto = userService.findUsers();
        return Response.of(getUsersDto);
    }

    @PatchMapping("/{userId}")
    public Response<Long> updateUser(
        @PathVariable Long userId,
        @Valid @RequestBody UpdateUserRequestDto dto
    ) {
        Long updatedUserId = userService.updateUser(userId, dto);
        return Response.of(updatedUserId);
    }

    @PostMapping("/{userId}/delete")
    public Response<Void> deleteUser(
        @PathVariable Long userId,
        @Valid @RequestBody DeleteUserRequestDto dto
    ) {
        userService.deleteUser(userId, dto);
        return Response.empty();
    }
}
