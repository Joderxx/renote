package top.renote.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.renote.model.RegisterUser;

import java.util.Date;

/**
 * Created by Joder_X on 2018/3/11.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RegisterRedisTest {
    @Autowired
    private RegisterRedis registerRedis;
    @Test
    public void add() throws Exception {
        RegisterUser registerUser = new RegisterUser("123","123","123@qq.com",new Date(), (byte) 1);
        registerRedis.add(registerUser);
    }

    @Test
    public void get() throws Exception {
        RegisterUser registerUser = registerRedis.get("123456");
        System.out.println(registerUser);
    }

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void add1() throws Exception {
    }

}