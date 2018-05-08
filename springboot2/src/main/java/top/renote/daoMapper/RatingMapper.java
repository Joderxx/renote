package top.renote.daoMapper;

import org.apache.ibatis.annotations.Mapper;
import top.renote.model.Rating;

/**
 * Created by Joder_X on 2018/2/27.
 */
@Mapper
public interface RatingMapper {

    int insertOne(Rating rating);

    Rating getOne(Rating rating);

    int updatePreference(Rating rating);
}
