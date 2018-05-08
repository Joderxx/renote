package top.renote.controller.note;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Joder_X on 2018/4/8.
 */
@Controller
public class ShowController {

    @GetMapping({"/shownote","/shownote.html"})
    public String show(){
        return "html/shownote.html";
    }
}
