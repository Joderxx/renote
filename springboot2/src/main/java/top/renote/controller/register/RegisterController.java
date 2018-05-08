package top.renote.controller.register;

import com.github.pagehelper.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import top.renote.dto.RegisterUserDTO;
import top.renote.service.register.RegisterService;
import top.renote.util.randomImage.ValidateCodeService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joder_X on 2017/12/11.
 */

@Controller
@Slf4j
public class RegisterController {

    @Autowired
    private RegisterService registerService;
    @Autowired
    private ValidateCodeService validateCodeService;

    @GetMapping({"/register","/register.html"})
    public String register(){
        return "/html/register.html";
    }

    @ResponseBody
    @PostMapping("/doRegister")
    public ModelMap doRegister(@Valid RegisterUserDTO registerUser, BindingResult result,HttpSession session){
        ModelMap modelMap = new ModelMap();
        List<String> error = new ArrayList<String>();
        if (result.hasErrors()){
            for (ObjectError objectError:result.getAllErrors()){
                error.add(objectError.getDefaultMessage());
            }
        }
        if (!validateCodeService.validateForID(session.getId(),registerUser.getVerificateCode())){
                error.add("验证码错误");
        }
        if (error.size()==0){
            int b = registerService.addInRegister(registerUser.toRegisterUser());
            switch (b){
                case RegisterService.HAS_ACCOUNT:error.add("账号已存在");
                case RegisterService.HAS_EMAIL:error.add("邮箱已存在");break;
            }
        }
        if (error.size()>0){
            modelMap.addAttribute("error",error);
            modelMap.addAttribute("status",403);
            return modelMap;
        }
        log.info("注册\tuser:{},pswd:{}",registerUser.getAccount(),registerUser.getPassword());
        modelMap.addAttribute("status",200);
        modelMap.addAttribute("info","账号注册成功，请速去激活");
        return modelMap;
    }

    @GetMapping("/activeAccount")
    public String active(@RequestParam("activeCode") String activeCode,@RequestParam("account") String account){
        List<String> error = new ArrayList<String>();
        if (StringUtil.isEmpty(activeCode)||StringUtil.isEmpty(account)){
            //信息不全
            return "/html/email.fail.html";

        }
        if (registerService.active(account,activeCode)){
            log.info("激活\tuser:{}",account);
            //成功
            return "/html/email.success.html";
        }
        //失败
        return "/html/email.fail.html";
    }

}
