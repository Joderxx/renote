package top.renote.util.digestUtil;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.Base64Utils;
import top.renote.daoMapper.UserMapper;

import static org.junit.Assert.*;

/**
 * Created by Joder_X on 2018/3/11.
 */
public class EncryptUtilTest {

    @Test
    public void test1(){
        byte[] b = Base64Utils.encode("{123:123}".getBytes());
        System.out.println(new String(b));
        System.out.println(new String(Base64Utils.decode(b)));

    }


}