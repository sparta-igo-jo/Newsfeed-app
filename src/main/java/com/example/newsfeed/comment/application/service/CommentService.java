package com.example.newsfeed.comment.application.service;

import com.example.newsfeed.comment.application.converter.CommentConverter;
import com.example.newsfeed.comment.dto.request.CreateCommentRequestDto;
import com.example.newsfeed.comment.dto.request.UpdateCommnetRequestDto;
import com.example.newsfeed.comment.dto.response.GetCommentResponseDto;
import com.example.newsfeed.comment.dto.response.UpdateCommentResponseDto;
import com.example.newsfeed.comment.entity.Comment;
import com.example.newsfeed.comment.exception.CommentNotFoundException;
import com.example.newsfeed.comment.exception.UnauthorizedUserException;
import com.example.newsfeed.comment.repository.CommentRepository;
import com.example.newsfeed.feed.application.service.FeedService;
import com.example.newsfeed.feed.entity.Feed;
import com.example.newsfeed.global.common.exception.ErrorDetail;
import com.example.newsfeed.user.application.service.UserService;
import com.example.newsfeed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.newsfeed.global.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final FeedService feedService;


    @Transactional
    public GetCommentResponseDto createComment(
            CreateCommentRequestDto dto,
            Long sessionUserId,
            Long feedId
    ) {
        User user = userService.findUserById(sessionUserId);
        Feed feed = feedService.findFeedByFeedId(feedId);

        Comment savedComment = Comment.builder()
                .content(dto.getContent())
                .user(user)
                .feed(feed)
                .build();

        commentRepository.save(savedComment);
        return GetCommentResponseDto.of(savedComment);
    }

    @Transactional(readOnly = true)
    public Page<GetCommentResponseDto> findCommentsByFeedId(Long feedId, Pageable pageable) {
        return commentRepository.findByFeedId(feedId, pageable)
                .map(CommentConverter::toResponse);
    }

    @Transactional
    public UpdateCommentResponseDto updateComment(
            Long commentId,
            Long sessionUserId,
            UpdateCommnetRequestDto dto
    ) {
        checkUserPermission(commentId, sessionUserId);
        Comment comment = findCommentByIdOrElseThrow(commentId);

        comment.updateContent(dto.getContent());

        return UpdateCommentResponseDto.of(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, Long sessionUserId) {
        checkUserPermission(commentId, sessionUserId);
        Comment comment = findCommentByIdOrElseThrow(commentId);

        commentRepository.delete(comment);
    }

    @Transactional
    public void deleteCommentsByUserId(Long userId) {
        if(!commentRepository.findByUserId(userId).isEmpty()) {
            commentRepository.deleteAll();
        }
    }

    // 해당 댓글을 찾을 수 없을 때 예외처리
    private Comment findCommentByIdOrElseThrow(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() ->
                        new CommentNotFoundException(List.of(
                                new ErrorDetail(COMMENT_NOT_FOUND, null, COMMENT_NOT_FOUND.getMessage())
                        ))
                );
    }

    // 자신이 해당 댓글의 작성자가 아닐 때의 예외처리
    private void checkUserPermission(Long commentId, Long sessionUserId) {
        if (!commentRepository.findUserIdByCommentId(commentId).equals(sessionUserId)) {
            throw new UnauthorizedUserException(List.of(
                    new ErrorDetail(COMMENT_ACCESS_DENIED, null, COMMENT_ACCESS_DENIED.getMessage())
            ));
        }
    }
}
