package top.renote.daoMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by Joder_X on 2018/4/2.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NotebookMapperTest {
    @Autowired
    private NotebookMapper notebookMapper;
    @Test
    public void getNbookByUId() throws Exception {
        System.out.println(notebookMapper.getNbookByUId(8L));
    }

}