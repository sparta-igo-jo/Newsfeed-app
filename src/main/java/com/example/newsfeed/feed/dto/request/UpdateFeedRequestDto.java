package com.example.newsfeed.feed.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@RequiredArgsConstructor
public class UpdateFeedRequestDto {

    @NotBlank
    private final Long feedId;

    @NotBlank
    private final String tile;

    private final String contents;

    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final MultipartFile feedImage;

}
