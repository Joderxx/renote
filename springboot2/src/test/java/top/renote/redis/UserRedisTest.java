package top.renote.redis;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import top.renote.model.User;
import top.renote.daoMapper.UserMapper;
import top.renote.util.randomImage.ImageCreate;

import java.awt.*;
import java.util.Date;

/**
 * Created by Joder_X on 2018/3/11.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRedisTest {
    @Autowired
    private UserRedis userRedis;
    @Test
    public void addUser() throws Exception {
        User user = new User(1L);
        user.setAccount("123");
        user.setSex((byte) 1);
        user.setUserName("Joder");
        user.setGmtLastLogin(new Date());
        user.setPassword("12345");
        userRedis.add(user);
    }

    @Test
    public void getUser() throws Exception {
        User user = userRedis.get("123123");
        System.out.println(user);
    }

    @Autowired
    private Font font;
    @Autowired
    private ImageCreate imageCreate;

    @Test
    public void test1(){
        Assert.assertEquals(font,imageCreate.getFont());
    }

    @Autowired
    private UserMapper userMapper;
    @Test
    public void test2(){
        System.out.println(userMapper.hashCode());
    }

    @Autowired
    private RedisTemplate<String,String> template;

    @Test
    public void test3(){
        template.opsForValue().set("A","我AB发");
    }

    @Test
    public void test4(){
        System.out.println(userRedis.get("12312"));
    }

}