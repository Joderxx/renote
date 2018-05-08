package top.renote.daoMapper;

import org.apache.ibatis.annotations.Mapper;
import top.renote.model.Login;

/**
 * Created by Joder_X on 2018/2/26.
 */
@Mapper
public interface LoginMapper {

    int insertOne(Login login);
}
