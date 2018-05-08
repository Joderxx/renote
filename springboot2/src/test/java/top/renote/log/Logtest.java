package top.renote.log;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Joder_X on 2018/3/19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Logtest {

    @Test
    public void test1(){
        log.info("{}","Hello World");
    }
}
