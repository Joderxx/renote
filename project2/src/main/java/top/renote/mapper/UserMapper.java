package top.renote.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.renote.model.User;

/**
 * Created by Joder_X on 2017/11/27.
 */
@Mapper
public interface UserMapper {

    User getUser(@Param("account") String account, @Param("password") String password);

    int updateLastLogin(User user);

}
