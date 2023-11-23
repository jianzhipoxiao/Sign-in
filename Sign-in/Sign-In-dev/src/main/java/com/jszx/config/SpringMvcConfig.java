package com.jszx.config;

import com.jszx.interceptor.LoginProtectInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 刘林
 * @version 1.0
 * @caeateDate 2023-11-15
 * @description mvc配置类， TODO ；添加拦截器会导致跨域问题暂时没解决
 */
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {
    @Autowired
    private LoginProtectInterceptor loginProtectInterceptor;

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://47.")
//                .allowedMethods("GET","POST","PUT","OPTIONS","DELETE","PATCH")
//                .allowCredentials(true).maxAge(3600);
//    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginProtectInterceptor)
//                .addPathPatterns("/Sign/**").excludePathPatterns("/Sign/getCarryKeyUser","/Sign/getRomeOnlineUsers");
    }
}
