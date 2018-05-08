package top.renote.controller.user;

import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import top.renote.model.User;
import top.renote.dto.AddressDTO;
import top.renote.dto.SuccessUser;
import top.renote.service.address.AddressService;
import top.renote.service.comment.AdviceService;
import top.renote.service.comment.CommentService;
import top.renote.service.note.NoteService;
import top.renote.service.user.UserService;
import top.renote.util.digestUtil.EncryptUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Joder_X on 2018/1/15.
 *
 */
@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private AddressService addressService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private AdviceService adviceService;

    @GetMapping("")
    public String userIndex(){
        return "html/user.html";
    }

    @ResponseBody
    @GetMapping("/info")
    public ModelMap userInfo(HttpServletRequest request){
        ModelMap modelMap = new ModelMap();
        User user = (User) request.getAttribute("user");
        modelMap.addAttribute("user",new SuccessUser(user));
        modelMap.addAttribute("status",200);
        return modelMap;
    }

    /**
     * 获得通知的数目
     * @param request
     * @return
     */
    @ResponseBody
    @GetMapping("/advice/count")
    public ModelMap adviceCount(HttpServletRequest request){
        ModelMap modelMap = new ModelMap();
        Long userId = (Long) request.getAttribute("userId");
        modelMap.addAttribute("count",adviceService.countUnAdvice(userId));
        modelMap.addAttribute("status",200);
        return modelMap;
    }

    @GetMapping("/advice")
    @ResponseBody
    public ModelMap advice(HttpServletRequest request){
        ModelMap modelMap = new ModelMap();
        Long userId = (Long) request.getAttribute("userId");
        modelMap.addAttribute("advice",adviceService.getAllAdvice(userId));
        modelMap.addAttribute("status",200);
        return modelMap;
    }

    @GetMapping("/like/notes")
    @ResponseBody
    public ModelMap getLike(HttpServletRequest request){
        ModelMap modelMap = new ModelMap();
        Long userId = (Long) request.getAttribute("userId");
        modelMap.addAttribute("notes",noteService.getLikeNotes(userId));
        modelMap.addAttribute("status",200);
        return modelMap;
    }

    @ResponseBody
    @GetMapping("/comments")
    public ModelMap getComments(HttpServletRequest request){
        Long userId = (Long) request.getAttribute("userId");
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("comments",commentService.getUserComments(userId));
        modelMap.addAttribute("status",200);
        return modelMap;
    }

    @ResponseBody
    @DeleteMapping("/comments/{commentId:\\d{1,18}}")
    public ModelMap deleteComment(@PathVariable("commentId") Long commentId){
        ModelMap modelMap = new ModelMap();
        if (commentService.deleteComment(commentId)){
            modelMap.addAttribute("info","删除成功");
            modelMap.addAttribute("status",200);
        }else {
            modelMap.addAttribute("error","删除失败");
            modelMap.addAttribute("status",500);
        }
        return modelMap;
    }

    @ResponseBody
    @PutMapping("/collect/notes/{noteId:\\d{1,18}}")
    public ModelMap collectNote(@PathVariable("noteId") Long noteId,HttpServletRequest request){
        Long userId = (Long) request.getAttribute("userId");
        ModelMap modelMap = new ModelMap();
        if (noteService.collectNote(userId,noteId)){
            modelMap.addAttribute("info","收藏成功");
            modelMap.addAttribute("status",200);
        }else {
            modelMap.addAttribute("error","收藏失败");
            modelMap.addAttribute("status",500);
        }
        return modelMap;
    }



    @ResponseBody
    @PutMapping("/notes/{noteId:\\d{1,18}}/share")
    public ModelMap sharedNote(@PathVariable("noteId") Long noteId,HttpServletRequest request){
        Long userId = (Long) request.getAttribute("userId");
        ModelMap modelMap = new ModelMap();
        AddressDTO addressDTO = addressService.sharedNote(userId,noteId);
        if (addressDTO!=null){
            modelMap.addAttribute("address",addressDTO);
            modelMap.addAttribute("status",200);
        }else {
            modelMap.addAttribute("error","分享失败");
            modelMap.addAttribute("status",500);
        }
        return modelMap;
    }

    @ResponseBody
    @GetMapping("/notes/{noteId:\\d{1,18}}/share")
    public ModelMap getSharedNote(@PathVariable("noteId") Long noteId,HttpServletRequest request){
        Long userId = (Long) request.getAttribute("userId");
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("address",addressService.getAddress(userId,noteId));
        modelMap.addAttribute("status",200);
        return modelMap;
    }

    @ResponseBody
    @DeleteMapping("/notes/{noteId:\\d{1,18}}/share")
    public ModelMap deleteAddress(@PathVariable("noteId") Long noteId,HttpServletRequest request){
        Long userId = (Long) request.getAttribute("userId");
        ModelMap modelMap = new ModelMap();
        if (addressService.deleteAddress(userId,noteId)){
            modelMap.addAttribute("info","删除成功");
            modelMap.addAttribute("status",200);
        }else {
            modelMap.addAttribute("error","删除失败");
            modelMap.addAttribute("status",500);
        }
        return modelMap;
    }

    @ResponseBody
    @PutMapping("/user/changePassword")
    public ModelMap changPass(@RequestParam("oldpass") String oldpass,
                              @RequestParam("newpass") String newpass,
                              HttpServletRequest request){
        User user = (User) request.getAttribute("user");
        ModelMap modelMap = new ModelMap();
        oldpass = EncryptUtil.SHA256(oldpass);
        newpass = EncryptUtil.SHA256(newpass);
        if (StringUtil.isEmpty(oldpass)||StringUtil.isEmpty(newpass)){
            modelMap.addAttribute("error","参数不齐");
            modelMap.addAttribute("status",403);
        }else if (!oldpass.equals(user.getPassword())){
            modelMap.addAttribute("error","密码不对");
            modelMap.addAttribute("status",403);
        }else {
            user.setPassword(newpass);
            if (userService.updatePass(user)){
                modelMap.addAttribute("info","修改成功");
                modelMap.addAttribute("status",200);
            }else {
                modelMap.addAttribute("error","服务器异常");
                modelMap.addAttribute("status",500);
            }
        }
        return modelMap;
    }
}
