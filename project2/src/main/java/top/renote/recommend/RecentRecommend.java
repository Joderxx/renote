package top.renote.recommend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.renote.dto.NoteDTO;
import top.renote.mapper.NoteMapper;
import top.renote.mapper.ScanMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joder_X on 2018/4/9.
 */
@Component
public class RecentRecommend {
    @Value("${recommend.recentSize}")
    private int recentSize;
    @Value("${python.neighborsize}")
    private int size;
    @Autowired
    private NeighborRecommend recommend;
    @Autowired
    private NoteMapper noteMapper;
    @Autowired
    private ScanMapper scanMapper;

    public List<NoteDTO> recommend(Long userId){
        List<NoteDTO> list = recommendByNote(userId);
        if (list==null||list.size()==0)return recommendByScan(userId);
        return list;
    }

    public List<NoteDTO> recommendByNote(Long userId){
        List<Long> ids = noteMapper.findRecent(userId);
        return recommend(ids);
    }

    public List<NoteDTO> recommendByScan(Long userId){
        List<Long> ids = scanMapper.findRecent(userId);
        return recommend(ids);
    }

    private List<NoteDTO> recommend(List<Long> ids){
        if (ids==null||ids.size()==0)return null;
        List<Long> noteIds = ids.subList(0,recentSize);
        List<NoteDTO> list = new ArrayList<>(recentSize*size);
        for (Long noteId:noteIds) {
            List<NoteDTO> ls = recommend.recommend(noteId);
            if (ls!=null&&ls.size()>0)
                list.addAll(ls);
        }
        return list;
    }
}
