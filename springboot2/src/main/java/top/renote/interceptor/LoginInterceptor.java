package top.renote.interceptor;

import com.github.pagehelper.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import top.renote.model.User;
import top.renote.dto.LoginDTO;
import top.renote.redis.UserRedis;
import top.renote.service.login.LoginService;
import top.renote.util.digestUtil.EncryptUtil;
import top.renote.util.json.JsonUtil;

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
        if(request.getAttribute("user")!=null){
            log.info(request.getAttribute("user").toString());
            return true;
        } else {
            log.info("{}没有登录",request.getRemoteAddr());
            redirect(response);
            return false;
        }

    }

    private void redirect(HttpServletResponse response) throws IOException {
       /* ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("error","没有登录,请登录...");
        response.setStatus(403);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(JsonUtil.modelToJson(modelMap));
        response.getWriter().flush();*/
        response.sendRedirect("/login");
    }
}
