package top.renote.service.notebook;

import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.renote.daoMapper.NotebookMapper;
import top.renote.model.Notebook;
import top.renote.model.Root;
import top.renote.model.User;
import top.renote.dto.NotebookDTO;
import top.renote.util.digestUtil.ChineseRoot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pokedo on 2018/1/27.
 */
@Service
public class NoteBookService {

    @Autowired
    NotebookMapper notebookMapper;

    /**
     * 判断笔记是否属于该用户
     * @param userId
     * @param nbId
     * @return
     */
    public boolean isBelong(Long userId,Long nbId){
        Notebook notebook = notebookMapper.getByNbIdAndUId(userId,nbId);
        return notebook!=null;
    }

    /**
     * 根据用户Id获取私有笔记本
     * @param id
     * @return
     */
    public List<NotebookDTO> getNbook(Long id){
        List<Notebook> notebooks = notebookMapper.getNbookByUId(id);
        List<NotebookDTO> list = new ArrayList<NotebookDTO>(notebooks.size());
        for (Notebook notebook:notebooks){
            list.add(new NotebookDTO(notebook, ChineseRoot.valueOf(notebook.getRoot().getRootName().toUpperCase())+"/"));
        }
        return list;
    }

    /**
     * 获取用户删除笔记本
     * @param userId
     * @return
     */
    public List<NotebookDTO> getDeleteNbook(Long userId){
        List<Notebook> notebooks = notebookMapper.getDelete(userId);
        List<NotebookDTO> list = new ArrayList<NotebookDTO>(notebooks.size());
        for (Notebook notebook:notebooks){
            list.add(new NotebookDTO(notebook,notebook.getRoot().getRootName()));
        }
        return list;
    }

    public NotebookDTO addOneNbook(Long userId,String name){
        if (StringUtil.isEmpty(name)) name = "新建笔记本";
        User user = new User();
        user.setUserId(userId);
        int i = 0;
        String t = name;
        while (notebookMapper.getHasNbook(userId,t)>0){
            t = name+"("+(++i)+")";
        }
        name = t;
        Date date = new Date();
        Notebook notebook = new Notebook(name,date,date,user, Root.PRIVATE,false);
        int num = notebookMapper.insertOne(notebook);
        return num>0?new NotebookDTO(notebook,ChineseRoot.PRIVATE.toString()):null;
    }

    public NotebookDTO updateName(Long nbId,String name){
        if (StringUtil.isEmpty(name))name = "新建笔记本";
        Long userId = notebookMapper.getUserId(nbId);
        int i = 0;
        String t = name;
        while (notebookMapper.getHasNbook(userId,t)>0){
            t = name+"("+(++i)+")";
        }
        name = t;
        Date date = new Date();
        Notebook notebook = new Notebook(nbId,name,null,date,null, null,false);
        int num = notebookMapper.updateName(notebook);
        if (num>0) {
            notebook = notebookMapper.getById(nbId);
            NotebookDTO notebookDTO = new NotebookDTO(notebook, ChineseRoot.valueOf(notebook.getRoot().getRootName().toUpperCase()).toString());
            return notebookDTO;
        }
        return null;
    }

    public NotebookDTO deleteOne(Long nbId,Boolean delete){
        Date date = new Date();
        Notebook notebook = new Notebook(nbId,null,null,date,null, null,delete);
        int num = notebookMapper.updateDelete(notebook);
        if (num>0) {
            notebook = notebookMapper.getById(nbId);
            NotebookDTO notebookDTO = new NotebookDTO(notebook, ChineseRoot.valueOf(notebook.getRoot().getRootName().toUpperCase()).toString());
            return notebookDTO;
        }
        return null;
    }

}
