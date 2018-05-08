package top.renote.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Joder_X on 2018/4/15.
 */
@Mapper
public interface ScanMapper {

    List<Long> findRecent(Long userId);
}
