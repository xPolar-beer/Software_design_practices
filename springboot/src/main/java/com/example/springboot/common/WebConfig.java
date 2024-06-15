package com.example.springboot.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements  WebMvcConfigurer {

    @Autowired
    AdminJwtInterceptor adminJwtInterceptor;

    @Autowired
    UserJwtInterceptor userJwtInterceptor;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // 指定controller统一的接口前缀
        configurer.addPathPrefix("/api", clazz -> clazz.isAnnotationPresent(RestController.class));
        //给注释了RestController加了个前缀
    }


    // 加自定义拦截器JwtInterceptor，设置拦截规则
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminJwtInterceptor).addPathPatterns("/api/user/**").excludePathPatterns("/api/admin/login", "/api/user/save", "/api/user/the/**");
        registry.addInterceptor(adminJwtInterceptor).addPathPatterns("/api/admin/**").excludePathPatterns("/api/admin/login");
        registry.addInterceptor(userJwtInterceptor).addPathPatterns("/api/user/the/**").excludePathPatterns("/api/admin/login", "/api/user/the/login");
    }


}

