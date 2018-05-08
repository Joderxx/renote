package top.renote.interceptor;

import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import top.renote.dto.LoginDTO;
import top.renote.model.User;
import top.renote.redis.UserRedis;
import top.renote.service.login.LoginService;
import top.renote.util.digestUtil.EncryptUtil;
import top.renote.util.json.JsonUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Joder_X on 2018/2/14.
 */
public class InterceptionUtil {

    public static void errorRedirect(HttpServletResponse response,String errorInfo,int status) throws IOException {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("error",errorInfo);
        response.setStatus(status);
        response.getWriter().print(JsonUtil.modelToJson(modelMap));
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().flush();
    }


}
