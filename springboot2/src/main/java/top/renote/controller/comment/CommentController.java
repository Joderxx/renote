package top.renote.controller.comment;

import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import top.renote.model.User;
import top.renote.dto.CommentDTO;
import top.renote.service.comment.AdviceService;
import top.renote.service.comment.CommentService;
import top.renote.util.digestUtil.AdviceString;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Joder_X on 2018/2/14.
 */
@RequestMapping("/notes/{noteId:\\d{1,18}}/comments")
@Controller
@Slf4j
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private AdviceService adviceService;
    @Value("${pageSize}")
    private int size;

    /**
     * 添加评论，需要前端从cookie中取出
     * @param noteId
     * @return
     */
    @ResponseBody
    @PostMapping({"","/"})
    public ModelMap addComment(@RequestParam(name = "content",defaultValue = "")String content,
                               @PathVariable("noteId") Long noteId,
                               HttpServletRequest request){
        ModelMap modelMap = new ModelMap();
        User user = null;
        if (request.getAttribute("user")!=null){
            user = (User) request.getAttribute("user");
        }else {
            log.info("comment 未进行登录，请登录");
            modelMap.addAttribute("error","未进行登录，请登录");
            modelMap.addAttribute("status",403);
            return modelMap;
        }
        log.info("user:{} , note:{} , content:{}",user.getUserId(),noteId,content);
        CommentDTO commentDTO = commentService.addComment(user,noteId,0L,content);
        if (commentDTO!=null){
            modelMap.addAttribute("comment",commentDTO);
            modelMap.addAttribute("status",200);
            adviceService.adviceAuthor(user.getUserId(),noteId,null,commentDTO.getCommentId(), AdviceString.NOTE.toString());
        }else {
            modelMap.addAttribute("error","输入信息不合法");
            modelMap.addAttribute("status",403);
        }
        return modelMap;
    }

    @ResponseBody
    @GetMapping({"","/"})
    public ModelMap getComments(@PathVariable("noteId") Long noteId,
                                @RequestParam(name = "page",defaultValue = "1",required = false) Integer pageNum){
        ModelMap modelMap = new ModelMap();
        PageHelper.startPage(pageNum,size);
        modelMap.addAttribute("comments",commentService.getComments(noteId));
        modelMap.addAttribute("status",200);
        return modelMap;
    }

    /**
     * 编写子评论
     * @param content
     * @param commentId
     * @param noteId
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/{commentId:\\d{1,18}}/child_comments")
    public ModelMap addChildComment(@RequestParam(name = "content",defaultValue = "")String content,
                                    @PathVariable("commentId") Long commentId,
                                    @PathVariable("noteId") Long noteId,
                                    HttpServletRequest request){
        ModelMap modelMap = new ModelMap();
        User user = null;
        if (request.getAttribute("user")!=null){
            user = (User) request.getAttribute("user");
        }else {
            modelMap.addAttribute("status",403);
            modelMap.addAttribute("error","未进行登录，请登录...");
            return modelMap;
        }
        log.info("user:{} , commentId:{} , content:{}",user.getUserId(),commentId,content);
        CommentDTO commentDTO = commentService.addComment(user,noteId,commentId,content);
        if (commentDTO!=null){
            modelMap.addAttribute("status",200);
            modelMap.addAttribute("comment",commentDTO);
            adviceService.adviceAuthor(user.getUserId(),noteId,commentId,commentDTO.getCommentId()
                    ,AdviceString.COMMENT.toString());
        }else {
            modelMap.addAttribute("error","输入信息不合法...");
            modelMap.addAttribute("status",403);
        }
        return modelMap;
    }

    @ResponseBody
    @GetMapping("/{commentId:\\d{1,18}}/child_comments")
    public ModelMap getChildComments(@PathVariable("commentId") Long commentId){
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("comments",commentService.getChildComments(commentId));
        modelMap.addAttribute("status",200);
        return modelMap;
    }

    @ResponseBody
    @GetMapping("/{commentId:\\d{1,18}}")
    public ModelMap like(@PathVariable("commentId") Long commentId,
                         @PathVariable("noteId") Long noteId,
                         @RequestParam(name = "like",defaultValue = "1") Integer like,
                         HttpServletRequest request){
        ModelMap modelMap = new ModelMap();
        int i = commentService.updateType(like,commentId);
        Long userId = null;
        if (request.getAttribute("userId")!=null)userId = (Long) request.getAttribute("userId");
        if (i>0){
            modelMap.addAttribute("status",200);
            modelMap.addAttribute("info","成功");
            if (userId!=null)
                adviceService.adviceAuthor(userId,noteId,commentId,null,AdviceString.LIKECOMMENT.toString());
        }else {
            modelMap.addAttribute("status",500);
            modelMap.addAttribute("error","服务器异常");
        }

        return modelMap;
    }

}
