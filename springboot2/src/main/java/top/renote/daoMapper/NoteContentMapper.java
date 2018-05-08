package top.renote.daoMapper;

import org.apache.ibatis.annotations.Mapper;
import top.renote.model.NoteContent;

import java.util.List;

/**
 * Created by Joder_X on 2018/1/12.
 */
@Mapper
public interface NoteContentMapper {
    /**
     * 添加一个
     * @param noteContent
     * @return
     */
    int insertOne(NoteContent noteContent);

    /**
     * 删除一个
     * @param id
     * @return
     */
    int deleteOne(Long id);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int deleteBatch(Long[] ids);

    /**
     * 修改内容 id，内容不为空，tag可选
     * @param noteContent
     * @return
     */
    int updateContent(NoteContent noteContent);

    /**
     * 修改标签 id，tag不为空
     * @param noteContent
     * @return
     */
    int updateTag(NoteContent noteContent);

    /**
     * 根据id查询内容
     * @return
     */
    NoteContent getNcById(Long id);

    /**
     * 获取所有公开的笔记
     * @return
     */
    List<NoteContent> getPublicNotesContent();

    /**
     * 根据笔记Id获得内容
     * @param noteId
     * @return
     */
    NoteContent getNcByNId(Long noteId);

    /**
     * 更新share
     * @param noteContent
     * @return
     */
    int updateShared(NoteContent noteContent);
}
