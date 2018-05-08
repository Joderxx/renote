package top.renote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.renote.mapper.UserMapper;
import top.renote.model.User;
import top.renote.util.EncryptUtil;

import java.util.Date;

/**
 * Created by Joder_X on 2018/1/20.
 */
@Service
public class LoginService {

    @Autowired
    private UserMapper userMapper;

    public User doLogin(String account,String password){
        password = EncryptUtil.SHA256(password);
        User user = userMapper.getUser(account,password);
        if (user==null)return null;
        user.setGmtLastLogin(new Date());
        userMapper.updateLastLogin(user);
        return user;
    }

}
