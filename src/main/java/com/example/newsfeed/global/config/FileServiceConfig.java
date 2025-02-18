package com.example.newsfeed.global.config;

import com.example.newsfeed.global.common.file.service.FileService;
import com.example.newsfeed.global.common.file.service.LocalFileService;
import com.example.newsfeed.user.application.service.UserReadService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileServiceConfig {

    @Bean
    public FileService fileService(UserReadService userService) {
        return new LocalFileService(userService);
    }
}
