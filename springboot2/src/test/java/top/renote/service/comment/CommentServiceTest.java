package top.renote.service.comment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.renote.model.User;

import static org.junit.Assert.*;

/**
 * Created by Joder_X on 2018/4/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceTest {
    @Autowired
    private CommentService commentService;

    @Test
    public void addComment() throws Exception {
        System.out.println(commentService.addComment(new User(8L),6994L,0L,"好"));
    }

    @Test
    public void test1(){
        System.out.println(commentService.getComments(6994L));
    }

}