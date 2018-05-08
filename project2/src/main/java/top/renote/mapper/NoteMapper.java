package top.renote.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.renote.model.Note;

import java.util.List;

/**
 * Created by Joder_X on 2018/3/21.
 */
@Mapper
public interface NoteMapper {

    /**
     * 根据id获取笔记
     * @param ids
     * @return
     */
    List<Note> findNotes(Long[] ids);

    /**
     * 返回用户笔记序列id，以修改时间排序
     * @param userId
     * @return
     */
    List<Long> findRecent(Long userId);


}
