package top.renote.interceptor;

import com.github.pagehelper.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import top.renote.model.User;
import top.renote.dto.LoginDTO;
import top.renote.redis.UserRedis;
import top.renote.service.LoginService;
import top.renote.util.EncryptUtil;
import top.renote.util.JsonUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Joder_X on 2018/1/15.
 * 拦截/user/**
 */
@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserRedis userRedis;
    @Autowired
    private LoginService loginService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[]cookies = request.getCookies();
        String hashId = null;
        String md5 = null;
        if (cookies==null){
            redirect(response);
            return false;
        }
        for (Cookie cookie:cookies){
            if ("md5".equals(cookie.getName()))
                md5 = cookie.getValue();
            else if ("user".equals(cookie.getName()))
                hashId = cookie.getValue();

        }
        if (StringUtil.isEmpty(hashId)||StringUtil.isEmpty(md5)){
            redirect(response);
            return false;
        }
        if (!md5.equals(EncryptUtil.md5(hashId))){
            redirect(response);
            return false;
        }
        LoginDTO loginDTO = JsonUtil.JsonToModel(EncryptUtil.base64Decode(hashId),LoginDTO.class);
        User user = userRedis.get(loginDTO.getAccount());
        Long userId = user==null?null:user.getUserId();
        if (userId==null){
            user = loginService.doLogin(loginDTO.getAccount(),loginDTO.getPassword());
            if (user==null){
                redirect(response);
                return false;
            }else {
                userRedis.add(user,1);
                userId = user.getUserId();
            }
        }
        request.setAttribute("userId",userId);
        request.setAttribute("user",user);//加入属性，以便下个拦截器用
        return true;
    }

    private void redirect(HttpServletResponse response) throws IOException {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("error","没有登录,请登录...");
        response.setStatus(403);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(JsonUtil.modelToJson(modelMap));
        response.getWriter().flush();
    }
}
