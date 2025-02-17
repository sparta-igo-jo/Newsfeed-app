package com.example.newsfeed.user.dto.response;

import com.example.newsfeed.user.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GetCommentResponseDto {

    private final Long id;
    private final String content;
    private final LocalDateTime createdDate;
    private final LocalDateTime updatedDate;
    private final String username;
    private final Long feedsId;

    public static GetCommentResponseDto fromEntity(Comment comment) {
        return GetCommentResponseDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdDate(comment.getCreatedAt())
                .updatedDate(comment.getUpdatedAt())
                .username(comment.getUser().getName())  // User 엔티티에서 이름 가져오기
                .feedsId(comment.getFeeds().getId())  // Feeds 엔티티에서 ID 가져오기
                .build();
    }
}
