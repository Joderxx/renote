package top.renote.daoMapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Joder_X on 2018/4/15.
 */
@Mapper
public interface ScanMapper {

    int getHasData(@Param("userId") Long userId, @Param("noteId") Long noteId);

    int updateTime(@Param("userId") Long userId, @Param("noteId") Long noteId);

    int insertData(@Param("userId") Long userId, @Param("noteId") Long noteId);
}
