package top.renote.controller.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import top.renote.redis.UserRedis;
import top.renote.service.login.LoginService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Joder_X on 2018/1/15.
 *
 */
@Controller
@Slf4j
public class LogoutController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private UserRedis userRedis;

    @GetMapping("/logout")
    public String loginOut(HttpServletResponse response,
                            HttpServletRequest request) throws IOException {
        //remove cookie

        Cookie cookie = new Cookie("user","");
//        cookie.setDomain(".renote.top");
        cookie.setPath("/");
//        cookie.setSecure(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        cookie = new Cookie("md5","");
        cookie.setMaxAge(0);
//        cookie.setDomain(".renote.top");
        cookie.setPath("/");
//        cookie.setSecure(true);
        response.addCookie(cookie);

        //redirect
        response.setStatus(200);
        return "redirect:/login";
    }

}
