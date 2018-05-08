package top.renote.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.renote.daoMapper.UserMapper;
import top.renote.model.User;

/**
 * Created by Joder_X on 2018/1/19.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据userId，获得user对象
     * @param userId
     * @return
     */
    public User getUser(Long userId){
        return userMapper.getUserById(userId);
    }

    public boolean updategmtLastLogin(User user){
        return userMapper.updateLastLogin(user)>0;
    }

    public boolean updatePass(User user){
        return userMapper.updatePassword(user)>0;
    }

    /**
     * 得到作者信息，用户名，头像，账号
     * @param noteId
     * @return
     */
    public User getAuthor(Long noteId){
        return userMapper.getUserByNId(noteId);
    }
}
