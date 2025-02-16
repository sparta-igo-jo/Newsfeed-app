package com.example.newsfeed.user.application.service;

import com.example.newsfeed.user.dto.request.AddCommentRequestDto;
import com.example.newsfeed.user.entity.Comment;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.repository.CommentRepository;
import com.example.newsfeed.user.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final FeedsRepository feedsRepository;

    public Comment save(Long id, AddCommentRequestDto addCommentRequest, String userName) {
        Optional<User> userOptional =  userRepository.findByEmail(userName);
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            System.out.println("사용자를 확인할수 없습니다. " + userName);
            return null;
        }

        Feeds feeds = FeedsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. " + id)
        );

        addCommentRequest.setUser(user);
        addCommentRequest.setFeed(feeds);

        return commentRepository.save(addCommentRequest.toEntity());
    }


}
