package com.elm.config;

import com.elm.interceptor.JWTInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    /**
     * 拦截器配置，拦截除了"/business", "/food", "/user/register", "/user/login" 以外的路由
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor())
                .addPathPatterns("/user", "/cart", "/deliveryAddress", "/orders", "/point", "/virtualWallet")
                .excludePathPatterns("/business", "/food", "/user/register/", "/user/login/");
    }
}
