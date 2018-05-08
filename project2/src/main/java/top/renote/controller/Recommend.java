package top.renote.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.renote.recommend.NeighborRecommend;
import top.renote.recommend.RecentRecommend;
import top.renote.recommend.RenoteRecommend;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Joder_X on 2018/3/21.
 */
@Controller
@Slf4j
class RecommendController {

    @Autowired
    private RenoteRecommend renoteRecommend;
    @Autowired
    private NeighborRecommend neighborRecommend;
    @Autowired
    private RecentRecommend recentRecommend;

    @ResponseBody
    @GetMapping("/user")
    public ModelMap recommendWithUser(HttpServletRequest request){
        ModelMap modelMap = new ModelMap();
        Long userId = (Long) request.getAttribute("userId");
        log.info("userId:{} itemCF",userId);
        modelMap.addAttribute("notes",renoteRecommend.itemCF(userId));
        modelMap.addAttribute("status",200);
        return modelMap;
    }

    @ResponseBody
    @GetMapping("/notes/{noteId:\\d{1,18}}")
    public ModelMap recommendWithItem(@PathVariable("noteId")Long noteId){
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("notes",neighborRecommend.recommend(noteId));
        log.info("noteID:{} recommend",noteId);
        modelMap.addAttribute("status",200);
        return modelMap;
    }

    @ResponseBody
    @GetMapping("/user/recent")
    public ModelMap recommendRecent(HttpServletRequest request){
        ModelMap modelMap = new ModelMap();
        Long userId = (Long) request.getAttribute("userId");
        log.info("userId:{} recent recommend",userId);
        modelMap.addAttribute("notes",recentRecommend.recommend(userId));
        modelMap.addAttribute("status",200);
        return modelMap;
    }
}
