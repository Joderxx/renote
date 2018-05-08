package top.renote.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.renote.interceptor.*;

/**
 * Created by Joder_X on 2018/3/11.
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    @Scope("prototype")
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/user","/user/**");
    }
}
