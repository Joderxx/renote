package top.renote.service.note;

import com.github.pagehelper.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.renote.daoMapper.NoteContentMapper;
import top.renote.daoMapper.NoteMapper;
import top.renote.daoMapper.NotebookMapper;
import top.renote.daoMapper.RatingMapper;
import top.renote.model.*;
import top.renote.dto.NoteDTO;
import top.renote.util.digestUtil.MyStringUtil;
import top.renote.util.python.TextClassification;
import top.renote.util.digestUtil.ChineseRoot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pokedo on 2018/1/28.
 */

/**
 *
 * 凡涉及笔记更新，一律检查修改权限(user_Id, creater_id)[service层或数据库层检查]
 */
@Service
@Transactional
@Slf4j
public class NoteService {

    private static final int cutSize = 100;

    @Autowired
    NoteMapper noteMapper;

    @Autowired
    private NoteContentMapper noteContentMapper;

    @Autowired
    private NotebookMapper notebookMapper;

    @Autowired
    private RatingMapper ratingMapper;

    @Autowired
    private TextClassification textClassification;

    /**
     * 判断笔记是否属于笔记本
     * @param nbId
     * @param noteId
     * @return
     */
    public boolean isBelong(Long nbId,Long noteId){
        return noteMapper.getByIdAndNbId(nbId,noteId)!=null;
    }

    public boolean isBelongUser(Long userId,Long noteId){
        return noteMapper.getByIdAndUId(userId,noteId)!=null;
    }

    /**
     * 获取某种类型的笔记
     * @param userId
     * @param type
     * @return
     */
    public List<NoteDTO> getNotes(Long userId,String type){
        List<Note> notes = noteMapper.getNoteByIdAndType(userId,type);
        List<NoteDTO> list = new ArrayList<NoteDTO>(notes.size());
        for (Note note:notes){
            NoteContent noteContent = note.getNoteContent();
            noteContent.setContent(MyStringUtil.cutString(noteContent.getContent(),cutSize));
            NoteDTO noteDTO = new NoteDTO(note,ChineseRoot.valueOf(type.toUpperCase()).toString());
            noteDTO.setEdit(note.getNoteContent().getCreateId().equals(userId));
            list.add(noteDTO);
        }
        return list;
    }

    /**
     * 获取用户删除笔记
     * @param userId
     * @return
     */
    public List<NoteDTO> getDeleteNotes(Long userId){
        List<Note> notes = noteMapper.getDeleteNoteById(userId);
        List<NoteDTO> list = new ArrayList<NoteDTO>(notes.size());
        for (Note note:notes){
            NoteContent noteContent = note.getNoteContent();
            noteContent.setContent(MyStringUtil.cutString(noteContent.getContent(),cutSize));
            String root = note.getNotebook().getRoot().getRootName();
            if (Root.PRIVATE.getRootName().equals(root)){
                NoteDTO noteDTO = new NoteDTO(note, ChineseRoot.PRIVATE+"/"+note.getNotebook().getNbookName());
                noteDTO.setEdit(false);
                list.add(noteDTO);
            }else{
                NoteDTO noteDTO = new NoteDTO(note,ChineseRoot.valueOf(root.toUpperCase()).toString());
                list.add(noteDTO);
            }
        }
        return list;
    }

    /**
     * 获取某本笔记本下的笔记
     * @param nbId
     * @return
     */
    public List<NoteDTO> getNotes(Long userId,Long nbId){
        List<Note> notes = noteMapper.getNotesInNbook(nbId);
        List<NoteDTO> list = new ArrayList<NoteDTO>(notes.size());
        for (Note note:notes){
            String root = note.getNotebook().getRoot().getRootName();
            NoteContent noteContent = note.getNoteContent();
            noteContent.setContent(MyStringUtil.cutString(noteContent.getContent(),cutSize));
            if (Root.PRIVATE.getRootName().equals(root)){
                NoteDTO noteDTO = new NoteDTO(note, ChineseRoot.PRIVATE+"/"+note.getNotebook().getNbookName());
                noteDTO.setEdit(true);
                list.add(noteDTO);
            }else{
                NoteDTO noteDTO = new NoteDTO(note,ChineseRoot.valueOf(root.toUpperCase()).toString());
                noteDTO.setEdit(note.getNoteContent().getCreateId().equals(userId));
                list.add(noteDTO);
            }
        }
        return list;
    }

    @Transactional
    public NoteDTO updateNote(Long userId,Long nbId,Long noteId,String name,String content,String tag){
        Note note = new Note(null,name,null,new Date(),null,null);
        note.setNoteId(noteId);
        NoteContent noteContent = noteContentMapper.getNcByNId(noteId);
        note.setNoteContent(noteContent);
        if (!StringUtil.isEmpty(name)){
            int i = 0;
            String t = name;
            while (noteMapper.getHasNote(nbId,noteId,t)>0){
                t = name+"("+(++i)+")";
            }
            name = t;
            note.setNoteName(name);
            int num = noteMapper.updateName(note);
            if (num>0&&StringUtil.isEmpty(content)){
                return new NoteDTO(note,"");
            }
        }
        if (!StringUtil.isEmpty(content)){
            if (noteContent.getCreateId().equals(userId)){
                noteContent.setContent(content);
                noteContentMapper.updateContent(noteContent);
                content = MyStringUtil.cuthtml(content);
                if (!StringUtil.isEmpty(tag))noteContent.setTag(tag);
                else if (content.length()>150){
                    noteContent.setTag(tag(noteId));
                }else{
                    noteContent.setTag("其它");
                }
                int num1 = noteContentMapper.updateTag(noteContent);
                noteMapper.updateTime(note);
                if (num1>0){
                    NoteDTO noteDTO = new NoteDTO(noteMapper.getNoteById(noteId),"");
                    noteDTO.setContent(MyStringUtil.cutString(noteContent.getContent(),cutSize));
                    return noteDTO;
                }
            }
        }
        return null;
    }

    /**
     * 添加笔记
     * @param nbId
     * @param name
     * @return
     */
    @Transactional
    public NoteDTO addNote(Long userId,Long nbId,String name,String type){
        if (StringUtil.isEmpty(name))name = "新建笔记";
        NoteDTO noteDTO = null;
        NoteContent noteContent = new NoteContent("","",userId,false,type);
        int num = noteContentMapper.insertOne(noteContent);
        String t = name;
        if (num>0){
            int i = 0;
            while (noteMapper.getHasNote(nbId,0L,t)>0){
                t = name+"("+(++i)+")";
            }
            name = t;
            Notebook notebook = notebookMapper.getById(nbId);
            Date date = new Date();
            Note note = new Note(false,name,date,date,notebook,noteContent);
            num = noteMapper.insertOneNote(note);
            if (num>0){
                note = noteMapper.getNoteById(note.getNoteId());
                noteDTO = new NoteDTO(note,ChineseRoot.PRIVATE+"/"+notebook.getNbookName());
            }
        }
        return noteDTO;
    }

    public NoteDTO getNote(Long noteId){
        Note note = noteMapper.getNoteById(noteId);
        NoteDTO noteDTO = null;
        if (note.getNotebook().getRoot().equals(Root.PRIVATE)) {
            noteDTO = new NoteDTO(note, ChineseRoot.PRIVATE + "/" + note.getNotebook().getNbookName());
        }else {
            noteDTO = new NoteDTO(note,ChineseRoot.valueOf(note.getNotebook()
                    .getRoot().getRootName().toUpperCase()).toString());
            noteDTO.setEdit(false);
        }
        return noteDTO;
    }

    public NoteDTO updatePath(Long noteId,Long aotherNb){
        int num = noteMapper.updateNb(noteId,aotherNb);
        if (num>0)return getNote(noteId);
        return null;
    }

    public boolean dropNote(Long noteId,boolean flag){
        int num = noteMapper.updateDelete(new Note(noteId,flag,null,null,new Date(),null,null));
        return num>0;
    }


    /**
     * 公开笔记
     * @param userId
     * @param noteId
     * @return
     */
    @Transactional
    public boolean publicNote(Long userId,Long noteId){
        NoteContent noteContent = noteContentMapper.getNcByNId(noteId);
        if (noteContent.getCreateId().equals(userId)){
            noteContent.setShared(true);
            int num = noteContentMapper.updateShared(noteContent);
            if (num>0){
                Note note = new Note(null,null,null,new Date(),null,noteContent);
                note.setNoteId(noteId);
                num = noteMapper.updateTime(note);
                ////////在public目录添加
                Notebook notebook = notebookMapper.getTypeNbook(userId,Root.PUBLIC.getRootName());
                note = noteMapper.getNoteById(noteId);
                note.setNotebook(notebook);
                noteMapper.insertOneNote(note);
                ///////////
                return num>0;
            }
        }
        return false;
    }

    /**
     * 取消收藏
     * @param noteId
     * @return
     */
    public boolean deleteCollect(Long noteId){
        return noteMapper.deleteOne(noteId)>0;
    }

    /**
     * 取消公开
     * @param noteId
     * @return
     */
    @Transactional
    public boolean deletePublic(Long noteId){
        return noteMapper.updateShare(noteId)>0&&noteMapper.deleteOne(noteId)>0;
    }


    /**
     * 判断笔记是否为公开或分享的
     * @param noteId
     * @return
     */
    public boolean isVisit(Long noteId){
        return noteMapper.isPublic(noteId)>0||noteMapper.isShared(noteId)>0;
    }

    @Transactional
    public boolean collectNote(Long userId,Long noteId){
        Notebook notebook = notebookMapper.getTypeNbook(userId,Root.COLLECT.getRootName());
        Note note = noteMapper.getNoteById(noteId);
        note.setNoteId(null);
        note.setNotebook(notebook);
        int num = noteMapper.insertOneNote(note);
        addRating(userId,noteId);//加rating
        log.info("插入一本笔记，noteID：{}",note.getNoteId());
        return num>0;
    }

    /**
     * 获得用户喜欢笔记
     * @param userId
     * @return
     */
    public List<NoteDTO> getLikeNotes(Long userId){
        List<Note> list = noteMapper.getLikeNotes(userId);
        List<NoteDTO> ls = new ArrayList<>(list.size());
        for (Note note:list){
            NoteDTO noteDTO = new NoteDTO(note);
            noteDTO.setEdit(false);
            ls.add(noteDTO);
        }
        return ls;
    }

    private boolean addRating(Long userId,Long noteId){
        Rating rating = ratingMapper.getOne(new Rating(null,null,userId,noteId));
        Double score = rating==null?4:rating.getPreference()+1.5;
        score = Math.min(5,score);
        if (rating==null){
            rating = new Rating(score,new Date().getTime(),userId,noteId);
            return ratingMapper.insertOne(rating)>0;
        }else {
            rating.setPreference(score);
            return ratingMapper.updatePreference(rating)>0;
        }
    }




    /**
     * 返回标签
     *
     * @return
     */
    private String tag(Long noteId){
        try {
            return textClassification.tag(noteId);
        } catch (IOException e) {
            log.info("noteId:{},出现IO异常",noteId);
        } catch (InterruptedException e) {
            log.info("noteId:{},出现Interrupted异常",noteId);
        }catch (Exception e){
            log.info("noteId:{},出现其他异常",noteId);
        }
        return "";
    }


}
