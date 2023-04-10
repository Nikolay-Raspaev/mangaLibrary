package com.LabWork.app;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*");
    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        ViewControllerRegistration registration = registry.addViewController("/notFound");
        registration.setViewName("forward:/index.html");
        registration.setStatusCode(HttpStatus.OK);

// Alternative way (404 error hits the console):
// > registry.addViewController("/notFound").setViewName("forward:/index.html");
    }

}