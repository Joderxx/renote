package top.renote.recommend;

import org.grouplens.lenskit.ItemRecommender;
import org.grouplens.lenskit.scored.ScoredId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.renote.dto.NoteDTO;
import top.renote.mapper.NoteMapper;
import top.renote.model.Note;
import top.renote.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Joder_X on 2018/3/14.
 */
@Component
public class RenoteRecommend {

    @Autowired
    @Qualifier("itemCF")
    private ItemRecommender itemCF;
    @Autowired
    @Qualifier("userCF")
    private ItemRecommender userCF;
    @Value("${recommend.size}")
    private int size;
    @Autowired
    private NoteMapper noteMapper;

    public List<NoteDTO> itemCF(Long userId) {
        List<ScoredId> scoredIds = itemCF.recommend(userId,size,null,null);
        return general(scoredIds);
    }

    public List<NoteDTO> userCF(Long userId) {
        List<ScoredId> scoredIds = userCF.recommend(userId,size,null,null);
        return general(scoredIds);
    }

    private List<NoteDTO> general(List<ScoredId> scoredIds){
        Long[] ids = new Long[scoredIds.size()];
        for (int i=0;i<size;i++){
            ids[i] = scoredIds.get(i).getId();
        }
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
