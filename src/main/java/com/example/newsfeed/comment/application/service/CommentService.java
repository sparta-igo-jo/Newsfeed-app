package com.example.newsfeed.comment.application.service;

import com.example.newsfeed.comment.application.converter.CommentConverter;
import com.example.newsfeed.comment.dto.request.CreateCommentRequestDto;
import com.example.newsfeed.comment.dto.response.GetCommentResponseDto;
import com.example.newsfeed.comment.entity.Comment;
import com.example.newsfeed.comment.exception.*;
import com.example.newsfeed.comment.repository.CommentRepository;
import com.example.newsfeed.feed.application.service.FeedService;
import com.example.newsfeed.feed.entity.Feed;
import com.example.newsfeed.user.application.service.UserService;
import com.example.newsfeed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final FeedService feedService;


    @Transactional
    public Long createComment(CreateCommentRequestDto dto, Long userId, Long feedId) {

        User user = userService.findUserById(userId);

        Feed feed = feedService.findFeedByFeedId(feedId);

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .user(user)
                .feed(feed)
                .build();

        commentRepository.save(comment);
        return comment.getFeed().getId();
    }

    @Transactional
    public void deleteComment(Long commentId, Long sessionUserId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException(List.of(new ErrorDetail("commentId", ErrorCode.COMMENT_NOT_FOUND.getMessage())))
        );

        if (comment.getUser() == null || !comment.getUser().getId().equals(sessionUserId)) {
            throw new UnauthorizedUserException(
                    List.of(new ErrorDetail("userId", ErrorCode.UNAUTHORIZED.getMessage()))
            );
        }

        commentRepository.delete(comment);
    }

    /**
     * 특정 피드의 댓글을 페이징해서 조회
     */
    @Transactional(readOnly = true)
    public Page<GetCommentResponseDto> findCommentsByFeedId(Long feedId, Pageable pageable) {
        return commentRepository.findByFeedId(feedId, pageable)
                .map(CommentConverter::toResponse);
    }
}
