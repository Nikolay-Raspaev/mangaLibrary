package com.LabWork.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.*;
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    public static final String REST_API = "/api";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*");
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseTrailingSlashMatch(true);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        ViewControllerRegistration registration = registry.addViewController("/notFound");
        registration.setViewName("forward:/index.html");
        registration.setStatusCode(HttpStatus.OK);
    }
}