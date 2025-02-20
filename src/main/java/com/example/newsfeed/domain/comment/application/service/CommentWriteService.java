package com.example.newsfeed.domain.comment.application.service;

import com.example.newsfeed.domain.comment.dto.request.CreateCommentRequestDto;
import com.example.newsfeed.domain.comment.dto.request.UpdateCommnetRequestDto;
import com.example.newsfeed.domain.comment.entity.Comment;
import com.example.newsfeed.domain.comment.repository.CommentRepository;
import com.example.newsfeed.domain.feed.application.service.FeedReadService;
import com.example.newsfeed.domain.feed.entity.Feed;
import com.example.newsfeed.common.exception.BaseException;
import com.example.newsfeed.common.exception.ErrorDetail;
import com.example.newsfeed.domain.user.application.service.UserReadService;
import com.example.newsfeed.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.newsfeed.common.exception.ErrorCode.COMMENT_ACCESS_DENIED;
import static com.example.newsfeed.common.exception.ErrorCode.COMMENT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CommentWriteService {

    private final CommentRepository commentRepository;
    private final UserReadService userReadService;
    private final FeedReadService feedReadService;

    @Transactional
    public Long createComment(
        CreateCommentRequestDto dto,
        Long sessionUserId,
        Long feedId
    ) {
        User findUser = userReadService.findUserById(sessionUserId);
        Feed findFeed = feedReadService.findFeedByIdOrThrow(feedId);

        Comment newComment = Comment.builder()
            .content(dto.getContent())
            .user(findUser)
            .feed(findFeed)
            .build();

        commentRepository.save(newComment);
        return newComment.getId();
    }

    @Transactional
    public Long updateComment(
        Long commentId,
        Long sessionUserId,
        UpdateCommnetRequestDto dto
    ) {
        Comment findComment = findCommentByIdOrThrow(commentId);
        checkUserPermission(findComment.getUser().getId(), sessionUserId);
        findComment.updateContent(dto.getContent());
        return findComment.getId();
    }

    @Transactional
    public void deleteComment(Long commentId, Long sessionUserId) {
        Comment findComment = findCommentByIdOrThrow(commentId);
        checkUserPermission(findComment.getUser().getId(), sessionUserId);
        Comment comment = findCommentByIdOrThrow(commentId);
        commentRepository.delete(comment);
    }

    @Transactional
    public void deleteCommentsByUserId(Long userId) {
        commentRepository.deleteByUserId(userId);
    }

    // 해당 댓글을 찾을 수 없을 때 예외처리
    private Comment findCommentByIdOrThrow(Long commentId) {
        return commentRepository.findById(commentId)
            .orElseThrow(() -> new BaseException(List.of(
                    new ErrorDetail(COMMENT_NOT_FOUND, null, COMMENT_NOT_FOUND.getMessage())
                )));
    }

    // 자신이 해당 댓글의 작성자가 아닐 때의 예외처리
    private void checkUserPermission(Long userId, Long sessionUserId) {
        if (!userId.equals(sessionUserId)) {
            throw new BaseException(List.of(
                new ErrorDetail(COMMENT_ACCESS_DENIED, null, COMMENT_ACCESS_DENIED.getMessage())
            ));
        }
    }
}
