package com.example.newsfeed.common.config;

import com.example.newsfeed.common.file.service.FileService;
import com.example.newsfeed.common.file.service.LocalFileService;
import com.example.newsfeed.domain.user.application.service.UserReadService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileServiceConfig {

    @Bean
    public FileService fileService(UserReadService userService) {
        return new LocalFileService(userService);
    }
}
