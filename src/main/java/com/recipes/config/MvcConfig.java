package com.recipes.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Value("${uploads-path}")
    private String uploadsPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String resLocPrefix = uploadsPath.startsWith("/") ? "file:" : "file:///";
        registry
                .addResourceHandler("/uploads/**")
                .addResourceLocations(resLocPrefix+uploadsPath);
    }
}
