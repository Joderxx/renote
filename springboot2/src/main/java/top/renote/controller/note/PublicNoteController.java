package top.renote.controller.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import top.renote.model.Interest;
import top.renote.model.Note;
import top.renote.model.User;
import top.renote.dto.NoteDTO;
import top.renote.service.comment.AdviceService;
import top.renote.service.note.InterestService;
import top.renote.service.note.NoteService;
import top.renote.service.user.UserService;
import top.renote.util.digestUtil.AdviceString;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Joder_X on 2018/2/26.
 * 查看公开笔记
 */
@Controller
@RequestMapping("/notes")
public class PublicNoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private InterestService interestService;
    @Autowired
    private UserService userService;
    @Autowired
    private AdviceService adviceService;

    @ResponseBody
    @GetMapping("/{noteId:\\d{1,18}}")
    public ModelMap getNotes(@PathVariable("noteId")Long noteId){
        ModelMap modelMap = new ModelMap();
        NoteDTO noteDTO = noteService.getNote(noteId);
        if (noteDTO!=null){
            noteDTO.setEdit(false);
            noteDTO.setPath(null);
            modelMap.addAttribute("note",noteDTO);
            modelMap.addAttribute("status",200);
        }else {
            modelMap.addAttribute("error","资源异常...");
            modelMap.addAttribute("status",500);
        }
        return modelMap;
    }

    @ResponseBody
    @PutMapping("/{noteId:\\d{1,18}}")
    public ModelMap evaluateNote(@PathVariable("noteId")Long noteId,
                                 @RequestParam(name = "like",defaultValue = "1")Byte like,
                                 HttpServletRequest request){
        ModelMap modelMap = new ModelMap();

        if (request.getAttribute("user")!=null){
            User user = (User)request.getAttribute("user") ;
            Interest interest = new Interest(like,user,new Note(noteId));
            if (interestService.insertOne(interest)){
                modelMap.addAttribute("info","成功");
                modelMap.addAttribute("status",200);
                adviceService.adviceAuthor(user.getUserId(),noteId,null,null, AdviceString.LIKENOTE.toString());
            }
            else {
                modelMap.addAttribute("error","已点过，不可重复");
                modelMap.addAttribute("status",403);
            }
        }else {
            modelMap.addAttribute("error","没有登录，请登陆...");
            modelMap.addAttribute("status",403);
        }
        return modelMap;
    }

    @GetMapping("/{noteId:\\d{1,18}}/islike")
    @ResponseBody
    public ModelMap isLiked(@PathVariable("noteId")Long noteId,
                            HttpServletRequest request){
        ModelMap modelMap = new ModelMap();
        if (request.getAttribute("user")!=null) {
            User user = (User) request.getAttribute("user");
            modelMap.addAttribute("info",interestService.isLike(user.getUserId(),noteId));
            modelMap.addAttribute("status",200);
        }else {
            modelMap.addAttribute("error","没有登录，请登陆...");
            modelMap.addAttribute("status",403);
        }
        return modelMap;
    }

    @GetMapping("/{noteId:\\d{1,18}}/userInfo")
    @ResponseBody
    public ModelMap getAuthor(@PathVariable("noteId")Long noteId){
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("author",userService.getAuthor(noteId));
        modelMap.addAttribute("status",200);
        return modelMap;
    }
}
