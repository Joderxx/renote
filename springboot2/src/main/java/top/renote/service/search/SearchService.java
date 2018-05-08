package top.renote.service.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.renote.daoMapper.NoteMapper;
import top.renote.dto.NoteDTO;
import top.renote.model.Note;
import top.renote.util.digestUtil.MyStringUtil;
import top.renote.util.python.Search;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joder_X on 2018/4/14.
 */
@Service
public class SearchService {
    private static final int cutSize = 100;

    @Autowired
    private NoteMapper noteMapper;
    @Autowired
    private Search search;
    public List<NoteDTO> search(String keyword){
        keyword = keyword.replaceAll("\\s+","+");
        Long[] ids = search.search(keyword);
        List<Note> list = noteMapper.findNotes(ids);
        List<NoteDTO> noteDTOS = new ArrayList<>(list.size());
        for (Note note:list){
            NoteDTO noteDTO = new NoteDTO(note);
            noteDTO.setContent(MyStringUtil.cutString(noteDTO.getContent(),cutSize));
            noteDTOS.add(noteDTO);
        }
        return noteDTOS;
    }
}
