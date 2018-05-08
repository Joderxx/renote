package top.renote.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by Joder_X on 2018/3/21.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteMapperTest {

    @Autowired
    private NoteMapper noteMapper;

    @Test
    public void findNotes() throws Exception {
        System.out.println(noteMapper.findNotes(new Long[]{1L,2L,3L}));
    }

    @Test
    public void findRecent() throws Exception {
        System.out.println(noteMapper.findRecent(9L).get(0));
    }

}