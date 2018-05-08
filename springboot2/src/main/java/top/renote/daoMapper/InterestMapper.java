package top.renote.daoMapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.renote.model.Interest;

/**
 * Created by Joder_X on 2018/1/12.
 */
@Mapper
public interface InterestMapper {
    /**
     * 插入一条信息
     * @param interest
     * @return
     */
    int insertOne(Interest interest);

    Interest getByUIdAndNId(Interest interest);

    int updateTypes(Interest interest);

    int isLike(@Param("userId") Long userId, @Param("noteId") Long noteId);
}
