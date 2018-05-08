package top.renote.controller.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.renote.model.User;
import top.renote.dto.LoginDTO;
import top.renote.dto.LoginUser;
import top.renote.redis.UserRedis;
import top.renote.service.login.LoginService;
import top.renote.util.digestUtil.EncryptUtil;
import top.renote.util.json.JsonUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Joder_X on 2018/1/15.
 *
 */
@Controller
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private UserRedis userRedis;

    @GetMapping({"/login","/login.html"})
    public String login(){
        return "/html/login.html";
    }

    @PostMapping("/doLogin")
    @ResponseBody
    public ModelMap doLogin(@Valid LoginUser loginUser,
                            HttpServletResponse response,
                            HttpServletRequest request,
                            BindingResult result) throws JsonProcessingException {
        ModelMap modelMap = new ModelMap();
        User user = loginService.doLogin(loginUser.getAccount(),loginUser.getPassword());
        List<String> error = new ArrayList<>();
        if (result.hasErrors()){
            for (ObjectError objectError: result.getAllErrors()){
                error.add(objectError.getDefaultMessage());
            }

        }
        if (user==null){
            error.add("账号或密码错误");
            modelMap.addAttribute("error",error);
            modelMap.addAttribute("status",403);
        }else{
            log.info("登录\tuser:{},pswd:{}",loginUser.getAccount(),loginUser.getPassword());//日志
            LoginDTO loginDTO = new LoginDTO(loginUser.getAccount(),loginUser.getPassword(),new Date());
            String hashId = EncryptUtil.base64Encode(JsonUtil.modelToJson(loginDTO));
            modelMap.addAttribute("info","登录成功");
            modelMap.addAttribute("status",200);
            addCookie(hashId, response,loginUser.getSelect()==true);
            addLogin(user.getUserId(),request.getRemoteAddr());
            if (loginUser.getSelect()==true)
                userRedis.add(user,30, TimeUnit.DAYS);//30天
            else
                userRedis.add(user,1);//1小时
        }
        return modelMap;
    }

    private void addLogin(Long userId,String ip){
        loginService.addLoginLog(userId,ip);
    }

    //todo
    private void addCookie(String hashId, HttpServletResponse response,boolean flag) {
        Cookie cookie = new Cookie("user",hashId);
        if (flag) cookie.setMaxAge(2592000);
//        cookie.setDomain(".renote.top");
        cookie.setPath("/");
//        cookie.setSecure(true);
        response.addCookie(cookie);

        String md5 = EncryptUtil.md5(hashId);//base md5摘要
        cookie = new Cookie("md5",md5);
        if (flag) cookie.setMaxAge(2592000);
//        cookie.setDomain(".renote.top");
        cookie.setPath("/");
//        cookie.setSecure(true);
        response.addCookie(cookie);
    }

}
