package top.renote.controller.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import top.renote.dto.NoteDTO;
import top.renote.dto.NotebookDTO;
import top.renote.service.note.NoteService;
import top.renote.service.notebook.NoteBookService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Joder_X on 2018/2/12.
 */
@RequestMapping("/user")
@Controller
public class DirectoryController {

    @Autowired
    private NoteService noteService;
    @Autowired
    private NoteBookService noteBookService;
    @Value("${pageSize}")
    private int size;

    @ResponseBody
    @GetMapping("/{type:^public$|^collect$|^shared$}")
    public ModelMap publicNotes(@PathVariable("type") String type,
                                @RequestParam(name = "page",defaultValue = "1")Integer pageNum,
                                HttpServletRequest request){
        ModelMap modelMap = new ModelMap();
        Long userId = (Long) request.getAttribute("userId");
        PageHelper.startPage(pageNum,size);
        List<NoteDTO> list = noteService.getNotes(userId,type);
        modelMap.addAttribute("notes",list);
        modelMap.addAttribute("status",200);
        return modelMap;
    }

    @ResponseBody
    @GetMapping("/private")
    public ModelMap privateNotes(HttpServletRequest request){
        ModelMap modelMap = new ModelMap();
        Long userId = (Long) request.getAttribute("userId");
        modelMap.addAttribute("notebooks",noteBookService.getNbook(userId));
        modelMap.addAttribute("status",200);
        return modelMap;
    }

    @ResponseBody
    @GetMapping("/recycle")
    public ModelMap recycleNotes(HttpServletRequest request){
        ModelMap modelMap = new ModelMap();
        Long userId = (Long) request.getAttribute("userId");
        List<NotebookDTO> nbooks = noteBookService.getDeleteNbook(userId);
        List<NoteDTO> notes = noteService.getDeleteNotes(userId);
        modelMap.addAttribute("status",200);
        modelMap.addAttribute("notebooks",nbooks);
        modelMap.addAttribute("notes",notes);
        return modelMap;
    }

    @ResponseBody
    @PutMapping("/recycle/note/{noteId:\\d{1,18}}/recovery")
    public ModelMap recoveryNote(@PathVariable("noteId") Long noteId){
        ModelMap modelMap = new ModelMap();
        if (noteService.dropNote(noteId,false)){
            modelMap.addAttribute("info","恢复成功");
            modelMap.addAttribute("status",200);
        }else {
            modelMap.addAttribute("error","恢复失败");
            modelMap.addAttribute("status",500);
        }
        return modelMap;
    }

    @ResponseBody
    @PutMapping("/recycle/notebook/{nbId:\\d{1,18}}/recovery")
    public ModelMap recoveryNotebook(@PathVariable("nbId") Long nbId){
        ModelMap modelMap = new ModelMap();
        NotebookDTO notebookDTO = noteBookService.deleteOne(nbId,false);
        if (notebookDTO!=null) {
            modelMap.addAttribute("status", 200);
            modelMap.addAttribute("info","恢复成功" );
        }else {
            modelMap.addAttribute("status", 500);
            modelMap.addAttribute("error","恢复失败" );
        }
        return modelMap;
    }

    /**
     * 查看分享的笔记内容
     * @param noteId
     * @return
     */
    @ResponseBody
    @GetMapping("/{type:^public$|^collect$|^shared$}/notes/{noteId:\\d{1,18}}")
    public ModelMap getCollectNote(@PathVariable("noteId") Long noteId){
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("note",noteService.getNote(noteId));
        modelMap.addAttribute("status",200);
        return modelMap;
    }

    @ResponseBody
    @DeleteMapping("/collect/notes/{noteId:\\d{1,18}}")
    public ModelMap cancelCollect(@PathVariable("noteId") Long noteId){
        ModelMap modelMap = new ModelMap();
        if (noteService.deleteCollect(noteId)){
            modelMap.addAttribute("status",200);
            modelMap.addAttribute("info","取消成功");
        }else {
            modelMap.addAttribute("status",500);
            modelMap.addAttribute("info","取消失败");
        }
        return modelMap;
    }

    @ResponseBody
    @DeleteMapping("/public/notes/{noteId:\\d{1,18}}")
    public ModelMap cancelPublic(@PathVariable("noteId") Long noteId){
        ModelMap modelMap = new ModelMap();
        if (noteService.deletePublic(noteId)){
            modelMap.addAttribute("status",200);
            modelMap.addAttribute("info","取消成功");
        }else {
            modelMap.addAttribute("status",500);
            modelMap.addAttribute("info","取消失败");
        }
        return modelMap;
    }


}
