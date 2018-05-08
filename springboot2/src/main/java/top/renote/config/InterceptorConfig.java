package top.renote.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.renote.interceptor.*;

/**
 * Created by Joder_X on 2018/3/11.
 */
@Configuration
//@Component
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    @Scope("prototype")
    public AutoLoginInterceptor autoLoginInterceptor(){
        return new AutoLoginInterceptor();
    }

    @Bean
    @Scope("prototype")
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }

    @Bean
    @Scope("prototype")
    public UserInteceptor userInteceptor(){
        return new UserInteceptor();
    }

    @Bean
    @Scope("prototype")
    public CollectInterceptor collectInterceptor(){
        return new CollectInterceptor();
    }

    @Bean
    @Scope("prototype")
    public CommentInterceptor commentInterceptor(){
        return new CommentInterceptor();
    }

    @Bean
    @Scope("prototype")
    public ShareInterceptor shareInterceptor(){
        return new ShareInterceptor();
    }

    @Bean
    @Scope("prototype")
    public NoteInterceptor noteInterceptor(){
        return new NoteInterceptor();
    }

    @Bean
    @Scope("prototype")
    public AllInterception allInterception(){
        return new AllInterception();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(allInterception()).addPathPatterns("/user","/user/**","/collect/**","/notes/**");
        registry.addInterceptor(autoLoginInterceptor()).addPathPatterns("/login");
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/user","/user/**");
        registry.addInterceptor(userInteceptor()).addPathPatterns("/user","/user/**");
        registry.addInterceptor(collectInterceptor()).addPathPatterns("/collect/notes/*");
        registry.addInterceptor(commentInterceptor()).addPathPatterns("/notes/*/comments","/notes/*/comments/*","/notes/*/comments/*/child_comments","/user/?*/comments/?*");
        registry.addInterceptor(shareInterceptor()).addPathPatterns("/user/notes/*/share");
        registry.addInterceptor(noteInterceptor()).addPathPatterns("/notes/*");

    }
}
