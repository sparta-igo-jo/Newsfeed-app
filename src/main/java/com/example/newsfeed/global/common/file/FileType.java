package com.example.newsfeed.global.common.file;

import com.example.newsfeed.global.common.exception.ErrorDetail;
import com.example.newsfeed.global.common.file.exception.UnSupportedFileTypeException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.newsfeed.global.common.constant.ImageUrlConst.DEFAULT_PROFILE_IMAGE_PATH;
import static com.example.newsfeed.global.common.exception.ErrorCode.UN_SUPPORTED_FILE_TYPE;

@Getter
@RequiredArgsConstructor
public enum FileType {

    PROFILE("profile"),
    FEEDS("feeds"),
    DEFAULT("default");

    private final String type;

    public static FileType from(String type) {
        for (FileType fileType : values()) {
            if (fileType.type.equalsIgnoreCase(type)) {
                return fileType;
            }
        }
        throw new UnSupportedFileTypeException(List.of(
            new ErrorDetail(UN_SUPPORTED_FILE_TYPE, "type", UN_SUPPORTED_FILE_TYPE.getMessage())
        ));
    }

    public String resolveSubDirectory(MultipartFile file, String defaultProFileImagePath) {
        if (this == DEFAULT && (file == null || file.isEmpty())) {
            return defaultProFileImagePath;
        }
        return this.getType();
    }
}
