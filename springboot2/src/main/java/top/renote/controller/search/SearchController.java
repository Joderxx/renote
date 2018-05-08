package top.renote.controller.search;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.renote.service.search.SearchService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Joder_X on 2018/4/14.
 */
@Slf4j
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @ResponseBody
    @GetMapping("/search")
    public ModelMap search(@RequestParam("keyword") String keyword, HttpServletRequest request){
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("notes",searchService.search(keyword));
        modelMap.addAttribute("status",200);
        log.info("ip:{} search keyword:{}",request.getRemoteAddr(),keyword);
        return modelMap;
    }
}
