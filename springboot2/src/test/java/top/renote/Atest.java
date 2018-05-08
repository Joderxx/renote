package top.renote;

import org.junit.Test;
import top.renote.dto.LoginDTO;
import top.renote.model.User;
import top.renote.redis.UserRedis;
import top.renote.util.digestUtil.EncryptUtil;
import top.renote.util.json.JsonUtil;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Joder_X on 2018/3/20.
 */
public class Atest {

    @Test
    public void test1(){
        Pattern pattern = Pattern.compile("<\\w+>(.+)</\\w+>");
        Matcher matcher = pattern.matcher("<html><p>aaaaa</p></html>");
        if (matcher.find())
            System.out.println(matcher.group(1));
    }

    @Test
    public void test2(){
        String s = "<html><p>aa>aaa</p></html>";
        System.out.println(s.replaceAll("<[^>]+>",""));
    }

    @Test
    public void test3(){
        String s = "<p><bean class='org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping'/><span style=\"font-weight: 700;\">Hello World</span></p>";
        String s1 = EncryptUtil.base64Encode(s);
        System.out.println(s1);
        s1 = "PHA+SGVsbG8gV29ybGTkvaDlpb08L3A+";
        System.out.println(EncryptUtil.base64Decode(s1));
    }


}
