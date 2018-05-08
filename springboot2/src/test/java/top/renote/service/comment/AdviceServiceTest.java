package top.renote.service.comment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by Joder_X on 2018/4/20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdviceServiceTest {

    @Autowired
    private AdviceService adviceService;
    @Test
    public void countUnAdvice() throws Exception {
        System.out.println(adviceService.countUnAdvice(8L));
    }

}