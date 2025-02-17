package com.example.newsfeed.user.application.service;

import com.example.newsfeed.global.common.exception.BaseException;
import com.example.newsfeed.global.common.exception.ErrorCode;
import com.example.newsfeed.user.dto.request.CreateCommentRequestDto;
import com.example.newsfeed.user.dto.response.GetCommentResponseDto;
import com.example.newsfeed.user.entity.Comment;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.repository.CommentRepository;
import com.example.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final FeedService feedService;

    /**
     * 댓글 생성
     */
    @Transactional
    public GetCommentResponseDto createComment(CreateCommentRequestDto requestDto, Long currentUserId, Long feedId) {

        User user = userService.findById(currentUserId);

        Feed feed = feedService.findById(feedId);

        Comment comment = Comment.builder()
                .content(requestDto.getContent())
                .user(user)
                .feed(feed)
                .build();

        commentRepository.save(comment);
        return GetCommentResponseDto.fromEntity(comment);
    }

    /**
     * 댓글 삭제 (유저 검증 추가)
     */
    @Transactional
    public void deleteComment(Long commentId, Long currentUserId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new BaseException(ErrorCode.COMMENT_NOT_FOUND, "commentId")
        );

        if (comment.getUser() == null || !comment.getUser().getId().equals(currentUserId)) {
            throw new BaseException(ErrorCode.UNAUTHORIZED, "userId");
        }

        commentRepository.delete(comment);
    }

    /**
     * 특정 피드의 댓글을 페이징해서 조회
     */
    @Transactional(readOnly = true)
    public Page<GetCommentResponseDto> findCommentsByFeedId(Long feedId, Pageable pageable) {
        return commentRepository.findByFeedId(feedId, pageable)
                .map(GetCommentResponseDto::fromEntity);
    }
}
