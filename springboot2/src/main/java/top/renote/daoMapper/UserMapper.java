package top.renote.daoMapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.renote.model.User;

/**
 * Created by Joder_X on 2017/11/27.
 */
@Mapper
public interface UserMapper {

    int gethasUserWithAccount(String account);

    int gethasUserWithEmail(String email);

    int insertOneUser(User user);

    User getUser(@Param("account") String account, @Param("password") String password);

    User getUserWithAccount(String account);

    User getUserById(Long userId);

    int updateLastLogin(User user);

    int updatePassword(User user);

    User getUserByNId(Long noteId);

    User getUserByCId(Long commentId);
}
