package top.renote.controller.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import top.renote.dto.NoteDTO;
import top.renote.service.address.AddressService;

/**
 * Created by pokedo on 2018/1/30.
 */
@Controller
public class AddressController {

    @Autowired
    private AddressService addressService;

    @ResponseBody
    @GetMapping("/sharedNotes/{note_address}")
    public ModelMap getShareNoteAddress(@PathVariable("note_address") String address){
        ModelMap modelMap = new ModelMap();
        NoteDTO noteDTO = addressService.getNoteByAddress(address);
        if(noteDTO != null){
            modelMap.addAttribute("status", 200);
            modelMap.addAttribute("note",noteDTO);
//            modelMap.addAttribute("info","成功添加笔记");
        } else {
            modelMap.addAttribute("status", 404);
            modelMap.addAttribute("error","该笔记已被删除或不存在");
        }
        return modelMap;
    }

}
