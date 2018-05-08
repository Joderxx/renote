package top.renote.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import top.renote.util.randomImage.ImageCreate;

import java.awt.*;

/**
 * Created by Joder_X on 2018/3/11.
 */
@Configuration
public class ImageConfig {

    @Value("${verification.font.name}")
    private String name;
    @Value("${verification.font.style}")
    private int style;
    @Value("${verification.font.size}")
    private int size;
    @Value("${verification.height}")
    private int height;
    @Value("${verification.width}")
    private int width;
    @Value("${verification.interference}")
    private boolean interference;
    @Value("${verification.random}")
    private String random;

    @Bean
    public Font font(){
        return new Font(name,style,size);
    }

    @Bean
    public ImageCreate imageCreate(){
        return new ImageCreate(random,width,height,font(),interference);
    }
}
