package com.example.newsfeed.user.dto.response;

import com.example.newsfeed.user.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCommentResponseDto {

    private Long id;
    private String content;
    private String createdDate;
    private String updatedDate;
    private String username;
    private Long feedsId;

    public static GetCommentResponseDto fromEntity(Comment comment) {
        return GetCommentResponseDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdDate(comment.getCreatedDate().toString())  // LocalDateTime -> String 변환
                .updatedDate(comment.getUpdatedDate().toString())
                .username(comment.getUser().getName())  // User 엔티티에서 이름 가져오기
                .feedsId(comment.getFeeds().getId())  // Feeds 엔티티에서 ID 가져오기
                .build();
    }
}
