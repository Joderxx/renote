package top.renote.interceptor;

import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import top.renote.model.User;
import top.renote.dto.LoginDTO;
import top.renote.redis.UserRedis;
import top.renote.service.login.LoginService;
import top.renote.service.user.UserService;
import top.renote.util.digestUtil.EncryptUtil;
import top.renote.util.json.JsonUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by Joder_X on 2018/2/11.
 */
public class AutoLoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserRedis userRedis;
    @Autowired
    private UserService userService;
    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[]cookies = request.getCookies();
        String hashId = null;
        String md5 = null;
        if (cookies==null){
            return true;
        }
        for (Cookie cookie:cookies){
            if ("user".equals(cookie.getName()))
                hashId = cookie.getValue();
            else if ("md5".equals(cookie.getName()))
                md5 = cookie.getValue();
        }
        if (StringUtil.isEmpty(hashId)||StringUtil.isEmpty(md5)){
            return true;
        }
        if (!md5.equals(EncryptUtil.md5(hashId)))return true;
        LoginDTO loginDTO = JsonUtil.JsonToModel(EncryptUtil.base64Decode(hashId),LoginDTO.class);
        if (loginDTO!= null){
            User user = loginService.doLogin(loginDTO.getAccount(),loginDTO.getPassword());
            if (user!=null){
                user.setGmtLastLogin(new Date());
                userService.updategmtLastLogin(user);
                userRedis.add(user,1);
                response.sendRedirect("/user");
                return false;
            }
        }
        return true;
    }

}
