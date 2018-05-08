package top.renote.daoMapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.renote.model.Notebook;

import java.util.List;

/**
 * Created by Joder_X on 2017/11/29.
 */
@Mapper
public interface NotebookMapper {

    /**
     * 插入一本笔记本
     * @param notebook
     * @return
     */
    public int insertOne(Notebook notebook);


    /**
     * 根据userId和nbId获取笔记本
     * @param userId
     * @param nbId
     * @return
     */
    public Notebook getByNbIdAndUId(@Param("userId") Long userId, @Param("nbId") Long nbId);

    /**
     * 获取某用户的private笔记本
     * @param userId
     * @return
     */
    List<Notebook> getNbookByUId(Long userId);

    /**
     * 获取删除笔记
     * @param userId
     * @return
     */
    List<Notebook> getDelete(Long userId);

    int getHasNbook(@Param("userId") Long userId, @Param("name") String name);

    /**
     * 更改笔记本名id和name,modified不为空
     * @param notebook
     * @return
     */
    int updateName(Notebook notebook);

    /**
     * 取得某笔记的用户id
     * @return
     */
    Long getUserId(Long nbId);

    /**
     * 根据笔记本id，获取笔记本
     * @param nbId
     * @return
     */
    Notebook getById(Long nbId);

    /**
     * 更新delete ,id/delete/gmtModified不为空
     * @param notebook
     * @return
     */
    int updateDelete(Notebook notebook);

    /**
     * 根据某用户id或的某种笔记本类型，
     * @param userId
     * @param type 可选public，collect,shared
     * @return
     */
    Notebook getTypeNbook(@Param("userId") Long userId, @Param("type") String type);

}
