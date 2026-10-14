package com.company.intra.config;

import com.company.intra.common.MenuInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** 공통 인터셉터 등록 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final MenuInterceptor menuInterceptor;

    public WebMvcConfig(MenuInterceptor menuInterceptor) {
        this.menuInterceptor = menuInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(menuInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/js/**", "/images/**", "/error",
                        "/login", "/logout", "/health", "/favicon.ico");
    }
}
