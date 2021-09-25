package com.xxxx.store.config;

import com.xxxx.store.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/** 注册处理器拦截器 */
@Configuration
public class LoginInterceptorConfigurer implements WebMvcConfigurer {
    /** 拦截器配置 */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 创建拦截器对象
        HandlerInterceptor interceptor = new LoginInterceptor();

        // 白名单
        List<String> patterns = new ArrayList<String>();
        patterns.add("/users/login");
        patterns.add("/users/token");
        patterns.add("/users/test");
        patterns.add("/users/reg");
        patterns.add("/users/test");
//        patterns.add("/users/cancellation");
//        patterns.add("/users/change_avatar");
//        patterns.add("/users/up");
        // 通过注册工具添加拦截器
        registry.addInterceptor(interceptor).addPathPatterns("/**").excludePathPatterns(patterns);
    }
}
