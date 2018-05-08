package top.renote.recommend;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.renote.dto.NoteDTO;
import top.renote.mapper.NeighborMapper;
import top.renote.mapper.NoteMapper;
import top.renote.model.Note;
import top.renote.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joder_X on 2018/3/21.
 */
@Component
@Slf4j
public class NeighborRecommend {

    @Autowired
    private NoteMapper noteMapper;
    @Autowired
    private NeighborMapper neighborMapper;

    public List<NoteDTO> recommend(Long noteId){
        String s = neighborMapper.findNeighbor(noteId);
        if (s==null||"".equals(s))return null;
        String[]ss = s.split("\\s+");
        Long[] ids = new Long[ss.length];
        for (int i=0;i<ss.length;i++) ids[i] = Long.parseLong(ss[i]);
        List<Note> list = noteMapper.findNotes(ids);
        List<NoteDTO> noteDTOS = new ArrayList<>(list.size());
        for (Note note:list){
            NoteDTO noteDTO = new NoteDTO(note);
            noteDTO.setContent(StringUtils.cutString(noteDTO.getContent(),100));
            noteDTOS.add(noteDTO);
        }
        return noteDTOS;

    }


}
