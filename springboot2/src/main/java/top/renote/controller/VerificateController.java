package top.renote.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import top.renote.util.randomImage.ValidateCodeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Joder_X on 2017/12/11.
 */
@Controller
public class VerificateController {

    @Autowired
    private ValidateCodeService validateCodeService;

    @GetMapping("/code")
    public void a(HttpServletRequest request, HttpServletResponse response){
        try {
            validateCodeService.createCode(request,response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
