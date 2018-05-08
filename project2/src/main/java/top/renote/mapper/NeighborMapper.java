package top.renote.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Joder_X on 2018/4/15.
 */
@Mapper
public interface NeighborMapper {

    /**
     * 根据id，返回相邻的id
     * @param id
     * @return
     */
    String findNeighbor(Long id);
}
