package top.renote.controller.notebook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import top.renote.dto.NotebookDTO;
import top.renote.service.notebook.NoteBookService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by pokedo on 2018/1/27.
 */
@RequestMapping("/user/notebooks")
@Controller
public class NoteBookController {

    @Autowired
    NoteBookService noteBookService;

    /**
     * 添加一本笔记本，在private
     * @param name 笔记本名
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping({"/",""})
    public ModelMap addNotebook(@RequestParam(name = "name") String name,
                                HttpServletRequest request){
        ModelMap modelMap = new ModelMap();
        Long userId = (Long) request.getAttribute("userId");
        NotebookDTO notebookDTO = noteBookService.addOneNbook(userId,name);
        if (notebookDTO!=null) {
            modelMap.addAttribute("notebook",notebookDTO );
            modelMap.addAttribute("status", 200);
        }else {
            modelMap.addAttribute("error","服务器错误，请重试..." );
            modelMap.addAttribute("status", 500);
        }
        return modelMap;
    }

    /**
     * 更新笔记本名
     * @param nbId
     * @param name
     * @return
     */
    @ResponseBody
    @PutMapping("/{nbId:\\d{1,18}}")
    public ModelMap updateName(@PathVariable("nbId")Long nbId,
                               @RequestParam(name = "name") String name){
        ModelMap modelMap = new ModelMap();
        NotebookDTO notebookDTO = noteBookService.updateName(nbId,name);
        if (notebookDTO!=null) {
            modelMap.addAttribute("status", 200);
            modelMap.addAttribute("notebook",notebookDTO );
        }else {
            modelMap.addAttribute("error","服务器错误，请重试..." );
            modelMap.addAttribute("status", 500);
        }
        return modelMap;
    }

    /**
     * 删除笔记
     * @param nbId
     * @return
     */
    @ResponseBody
    @DeleteMapping("/{nbId:\\d{1,18}}")
    public ModelMap deleteNbook(@PathVariable("nbId")Long nbId){
        ModelMap modelMap = new ModelMap();
        NotebookDTO notebookDTO = noteBookService.deleteOne(nbId,true);
        if (notebookDTO!=null) {
            modelMap.addAttribute("status", 200);
            modelMap.addAttribute("info","删除成功..." );
        }else {
            modelMap.addAttribute("status", 500);
            modelMap.addAttribute("error","删除失败..." );
        }
        return modelMap;
    }
}
