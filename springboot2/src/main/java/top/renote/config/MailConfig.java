package top.renote.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

/**
 * Created by Joder_X on 2018/3/11.
 */
@Configuration
public class MailConfig {

    @Bean
    public SimpleMailMessage simpleMailMessage(){
        return new SimpleMailMessage();
    }
}
