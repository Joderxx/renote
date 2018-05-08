package top.renote.service.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.renote.daoMapper.LoginMapper;
import top.renote.daoMapper.UserMapper;
import top.renote.model.Login;
import top.renote.model.User;
import top.renote.util.digestUtil.EncryptUtil;

import java.util.Date;

/**
 * Created by Joder_X on 2018/1/20.
 */
@Service
public class LoginService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoginMapper loginMapper;

    public User doLogin(String account,String password){
        password = EncryptUtil.SHA256(password);
        User user = userMapper.getUser(account,password);
        if (user==null)return null;
        user.setGmtLastLogin(new Date());
        userMapper.updateLastLogin(user);
        return user;
    }

    public boolean addLoginLog(Long userId,String ip){
        return loginMapper.insertOne(new Login(ip,new Date(),new User(userId)))>0;
    }
}
