package top.renote.service.note;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.renote.dto.NoteDTO;

import static org.junit.Assert.*;

/**
 * Created by Joder_X on 2018/4/2.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteServiceTest {

    @Autowired
    private NoteService noteService;

    @Test
    public void updateNote() throws Exception {
        NoteDTO dto = noteService.updateNote(9L,18L,6982L,"123","12345",null);
        System.out.println(dto);
    }

    @Test
    public void addNote() throws Exception {
        System.out.println(noteService.addNote(9L,18L,"","text"));
    }

    @Test
    public void deletePublic() throws Exception {
        noteService.deletePublic(6995L);
    }

    @Test
    public void deleteCollect() throws Exception {
        noteService.deleteCollect(6998L);
    }
}