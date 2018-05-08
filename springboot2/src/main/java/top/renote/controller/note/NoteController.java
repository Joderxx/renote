package top.renote.controller.note;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import top.renote.dto.NoteDTO;
import top.renote.service.address.AddressService;
import top.renote.service.note.NoteService;
import top.renote.util.digestUtil.EncryptUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by pokedo on 2018/1/28.
 */

@RequestMapping("/user/notebooks/{nbId:\\d{1,18}}/notes")
@Controller
@Slf4j
public class NoteController {

    @Autowired
    NoteService noteService;
    @Autowired
    AddressService addressService;
    @Value("${pageSize}")
    private int size;

    @ResponseBody
    @GetMapping({"","/"})
    public ModelMap getNotesInNbook(@PathVariable("nbId")Long nbId,
                                    @RequestParam(name = "page",defaultValue = "1")Integer pageNum,
                                    HttpServletRequest request){
        ModelMap modelMap = new ModelMap();
        Long userId = (Long) request.getAttribute("userId");
        PageHelper.startPage(pageNum,size);
        List<NoteDTO> list = noteService.getNotes(userId,nbId);
        modelMap.addAttribute("notes",list);
        modelMap.addAttribute("status",200);
        return modelMap;
    }

    /**
     * 添加一个笔记
     * @param nbId
     * @param name
     * @return
     */
    @ResponseBody
    @PostMapping({"/",""})
    public ModelMap addNote(@PathVariable("nbId") Long nbId,
                            @RequestParam("name") String name,
                            @RequestParam("type") String type,
                            HttpServletRequest request){
        log.info("nbId:{},name:{},type:{}",nbId,name,type);
        ModelMap modelMap = new ModelMap();
        Long userId = (Long) request.getAttribute("userId");
        NoteDTO noteDTO = noteService.addNote(userId,nbId,name,type);
        generalCode(modelMap,noteDTO);
        return modelMap;
    }

    /**
     * 更改笔记
     * @param nbId
     * @param noteId
     * @param name
     * @param content
     * @param request
     * @return
     */
    @ResponseBody
    @PutMapping("/{noteId:\\d{1,18}}")
    public ModelMap updateNote(@PathVariable("nbId") Long nbId,
                               @PathVariable("noteId")Long noteId,
                               @RequestParam("name") String name,
                               @RequestParam(name = "content",defaultValue = "") String content,
                               @RequestParam(name = "tag",defaultValue = "") String tag,
                               HttpServletRequest request){
        ModelMap modelMap = new ModelMap();
        content = content.replaceAll("\\s+","+");
        content = EncryptUtil.base64Decode(content);//解码
        Long userId = (Long) request.getAttribute("userId");
        NoteDTO noteDTO = noteService.updateNote(userId,nbId,noteId,name,content,tag);
        generalCode(modelMap,noteDTO);
        return modelMap;

    }

    /**
     * 获得一份笔记
     * @param noteId
     * @return
     */
    @ResponseBody
    @GetMapping("/{noteId:\\d{1,18}}")
    public ModelMap getNote(@PathVariable("noteId") Long noteId){
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("note",noteService.getNote(noteId));
        modelMap.addAttribute("status",200);
        return modelMap;
    }

    /**
     * 把笔记移到另外一本笔记本
     * @param noteId
     * @param anotherNb
     * @return
     */
    @ResponseBody
    @PutMapping("/{noteId:\\d{1,18}}/transfer/notebooks/{anotherNb:\\d{1,18}}")
    public ModelMap moveNote(@PathVariable("noteId") Long noteId,
                             @PathVariable("anotherNb") Long anotherNb){
        ModelMap modelMap = new ModelMap();
        NoteDTO noteDTO = noteService.updatePath(noteId,anotherNb);
        generalCode(modelMap,noteDTO);
        return modelMap;
    }

    /**
     * 把笔记丢到回收站
     * @param noteId
     * @return
     */
    @ResponseBody
    @DeleteMapping("/{noteId:\\d{1,18}}")
    public ModelMap dropNote(@PathVariable("noteId") Long noteId){
        ModelMap modelMap = new ModelMap();
        if (noteService.dropNote(noteId,true)){
            modelMap.addAttribute("info","删除成功...");
            modelMap.addAttribute("status",200);
        }else {
            modelMap.addAttribute("info","删除失败...");
            modelMap.addAttribute("status",500);
        }
        return modelMap;
    }


    @ResponseBody
    @PutMapping("/{noteId:\\d{1,18}}/public")
    public ModelMap publicNote(@PathVariable("noteId")Long noteId,HttpServletRequest request){
        ModelMap modelMap = new ModelMap();
        Long userId = (Long) request.getAttribute("userId");
        if (noteService.publicNote(userId,noteId)){
            modelMap.addAttribute("info","公开笔记成功...");
            modelMap.addAttribute("status",200);
        }else {
            modelMap.addAttribute("error","没有权限...");
            modelMap.addAttribute("status",403);
        }
        return modelMap;
    }

    private void generalCode(ModelMap modelMap,NoteDTO noteDTO){
        if (noteDTO!=null){
            modelMap.addAttribute("note",noteDTO);
            modelMap.addAttribute("status",200);
        }else {
            modelMap.addAttribute("error","服务器异常，请重试...");
            modelMap.addAttribute("status",500);
        }
    }

}
