package top.renote.interceptor;

import com.github.pagehelper.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import top.renote.dto.LoginDTO;
import top.renote.model.User;
import top.renote.redis.UserRedis;
import top.renote.service.login.LoginService;
import top.renote.util.digestUtil.EncryptUtil;
import top.renote.util.json.JsonUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by Joder_X on 2018/4/15.
 * 拦截所有url
 */
@Slf4j
public class AllInterception extends HandlerInterceptorAdapter {

    @Autowired
    private UserRedis userRedis;
    @Autowired
    private LoginService loginService;


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[]cookies = request.getCookies();
        if (cookies==null)return true;
        String hashId = null;
        String md5 = null;
        for (Cookie cookie:cookies){
            if ("md5".equals(cookie.getName()))
                md5 = cookie.getValue();
            else if ("user".equals(cookie.getName()))
                hashId = cookie.getValue();
        }
        if (StringUtil.isEmpty(hashId)||StringUtil.isEmpty(md5)){
            return true;
        }
        if (!md5.equals(EncryptUtil.md5(hashId))){
            return true;
        }
        LoginDTO loginDTO = JsonUtil.JsonToModel(EncryptUtil.base64Decode(hashId),LoginDTO.class);
        User user = userRedis.get(loginDTO.getAccount());
        Long userId = user==null?null:user.getUserId();
        if (userId==null){
            user = loginService.doLogin(loginDTO.getAccount(),loginDTO.getPassword());
            if (user!=null){
                userRedis.add(user,1);
                userId = user.getUserId();
                request.setAttribute("userId",userId);
                request.setAttribute("user",user);//加入属性，以便下个拦截器用
            }
        }else{
            request.setAttribute("userId",userId);
            request.setAttribute("user",user);//加入属性，以便下个拦截器用
        }
        return true;
    }


}
