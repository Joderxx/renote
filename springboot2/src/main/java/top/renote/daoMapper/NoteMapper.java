package top.renote.daoMapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.renote.model.Note;

import java.util.List;

/**
 * Created by Joder_X on 2017/11/28.
 */
@Mapper
public interface NoteMapper {

    /**
     * 插入一个笔记
     * @param note
     * @return
     */
    public int insertOneNote(Note note);

    /**
     * 批量增加,content,tag不能weinull
     * @param list
     * @return
     */
    public int insertBatchNote(List<Note> list);

    /**
     * 取得所有笔记
     * @return
     */
    public List<Note> getAllNote();

    /**
     * 根据Id获得笔记
     * @param id
     * @return
     */
    public Note getNoteById(Long id);

    /**
     * 删除一条记录
     * @param id
     * @return
     */
    public int deleteOne(Long id);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    public int deleteBatch(Long[] ids);

    /**
     * 更新名字，id,名字，时间不能为空
     * @param note
     * @return
     */
    public int updateName(Note note);

    /**
     * 更新时间
     * @param note
     * @return
     */
    public int updateTime(Note note);

    /**
     * 修改isDelete,id,isDelete,时间不为空
     * @param note
     * @return
     */
    public int updateDelete(Note note);

    /**
     * 查询用户所有删除的笔记
     * @return
     */
    public List<Note> getDeleteNoteById(Long id);

    /**
     * 查询用户所有某种类型笔记
     * @param id
     * @param type 可选 public，collect，share
     * @return
     */
    public List<Note> getNoteByIdAndType(@Param("id") Long id, @Param("type") String type);

    Note getByIdAndNbId(@Param("nbId") Long nbId, @Param("noteId") Long noteId);

    Note getByIdAndUId(@Param("userId") Long userId, @Param("noteId") Long noteId);

    /**
     * 获取某本笔记本的笔记
     * @param nbId
     * @return
     */
    List<Note> getNotesInNbook(Long nbId);

    /**
     * 根据nbId和name查看是否存在相同名字笔记
     * @param nbId
     * @param name
     * @return
     */
    int getHasNote(@Param("nbId") Long nbId,@Param("noteId")Long noteId, @Param("name") String name);

    int updateNb(@Param("noteId") Long noteId, @Param("nbId") Long aotherNb);

    /**
     * 是否为公开笔记
     * @param noteId
     * @return
     */
    int isPublic(Long noteId);

    /**
     * 是否为分享的笔记，与字段shared无关
     * @param noteId
     * @return
     */
    int isShared(Long noteId);

    Note getNoteByAddress(String address);

    /**
     * 根据id获取笔记
     * @param ids
     * @return
     */
    List<Note> findNotes(Long[] ids);

    Long getTheNote(@Param("nbId") Long nbId, @Param("name") String name);

    /**
     * 取消公开
     * @param noteId
     * @return
     */
    int updateShare(Long noteId);


    List<Note> getLikeNotes(Long userId);

}
