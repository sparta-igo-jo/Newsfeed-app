package com.example.newsfeed.user.application.service;

import com.example.newsfeed.user.dto.request.CreateCommentRequestDto;
import com.example.newsfeed.user.dto.response.GetCommentResponseDto;
import com.example.newsfeed.user.entity.Comment;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.repository.CommentRepository;
import com.example.newsfeed.user.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Getter
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final FeedsRepository feedsRepository;

    /**
     * 댓글 생성
     */
    @Transactional
    public Comment createComment(CreateCommentRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을수 없습니다.")
        );

        Feeds feeds = feedsRepository.findById(requestDto.getFeedsId()).orElseThrow(
                () -> new IllegalArgumentException("게스글을 찾을 수 없습니다.")
        );

        Comment comment = requestDto.toEntity(user, feeds);
        return commentRepository.save(comment);
    }

    /**
     * 댓글 삭제
     */
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글을 찾을수 없습니다.")
        );

        commentRepository.delete(comment);
    }

    /**
     * 특정 피드의 댓글을 페지이해서 조회
     */
    @Transactional(readOnly = true)
    public Page<GetCommentResponseDto> findCommentsByFeedId(Long feedsId, Pageable pageable) {
        Page<Comment> commentsPage = commentRepository.findByFeedsId(feedsId, pageable);

        return commentsPage.map(GetCommentResponseDto::fromEntity);
    }
}
