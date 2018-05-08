package top.renote.controller.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Joder_X on 2018/3/15.
 */
@Controller
public class IndexController {

    @GetMapping({"","/","/index"})
    public String index(){
        return "html/index.html";
    }
}
