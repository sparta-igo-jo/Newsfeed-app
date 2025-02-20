package com.example.newsfeed.comment.application.service;

import com.example.newsfeed.comment.application.converter.CommentConverter;
import com.example.newsfeed.comment.dto.response.GetCommentResponseDto;
import com.example.newsfeed.comment.entity.Comment;
import com.example.newsfeed.comment.repository.CommentRepository;
import com.example.newsfeed.feed.application.service.FeedReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentReadService {

    private final CommentRepository commentRepository;
    private final FeedReadService feedReadService;

    public Page<GetCommentResponseDto> findCommentsByFeedId(
        Long feedId,
        Pageable pageable
    ) {
        feedReadService.findFeedByIdOrThrow(feedId);
        Page<Comment> comments = commentRepository.findByFeedId(feedId, pageable);
        return CommentConverter.toResponse(comments);
    }
}
