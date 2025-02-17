package com.example.newsfeed.user.dto.response;

import com.example.newsfeed.user.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetCommentResponseDto {

    private final Long id;
    private final String content;
    private final LocalDateTime createdDate;
    private final LocalDateTime updatedDate;
    private final String username;
    private final Long feedId;

    public GetCommentResponseDto(Long id, String content, LocalDateTime createdDate, LocalDateTime updatedDate, String username, Long feedId) {
        this.id = id;
        this.content = content;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.username = username;
        this.feedId = feedId;
    }

    /**
     * DTO 객체 생성
     */
    public static GetCommentResponseDto of(
            Long id,
            String content,
            LocalDateTime createdDate,
            LocalDateTime updatedDate,
            String username,
            Long feedId
    ) {
            return new GetCommentResponseDto(id, content, createdDate, updatedDate, username, feedId);
    }

    /**
     * 엔티티를 DTO로 변환
     */
    public static GetCommentResponseDto fromEntity(Comment comment) {
        return GetCommentResponseDto.of(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                comment.getUser() != null ? comment.getUser().getName() : "알수 없음",
                comment.getFeed() != null ? comment.getFeed().getId() : null
        );
    }
}
