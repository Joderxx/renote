package top.renote.service.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.renote.daoMapper.AddressMapper;
import top.renote.daoMapper.NoteMapper;
import top.renote.daoMapper.NotebookMapper;
import top.renote.model.*;
import top.renote.dto.AddressDTO;
import top.renote.dto.NoteDTO;
import top.renote.model.*;
import top.renote.util.digestUtil.ChineseRoot;
import top.renote.util.digestUtil.RandomCode;

import java.util.Date;

/**
 * Created by pokedo on 2018/1/30.
 */
@Service
public class AddressService {

    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private NoteMapper noteMapper;
    @Autowired
    private NotebookMapper notebookMapper;

    @Transactional
    public AddressDTO sharedNote(Long userId,Long noteId){
        String address = null;
        SharedNoteAddress add = addressMapper.getAddressByNote(userId, noteId);
        if(add != null) return new AddressDTO(add);
        do {
            address = RandomCode.randString();
        }while (addressMapper.getRepeatAddress(address)>0);
        /////////////在share目录添加
        Notebook notebook = notebookMapper.getTypeNbook(userId, Root.SHARED.getRootName());
        Note note = noteMapper.getNoteById(noteId);

        note.setNoteId(null);
        note.setNotebook(notebook);
        note.setGmtModified(new Date());    //所有在share，collect，private目录下新添加的note都设置modified时间，除移动笔记本除外
        noteMapper.insertOneNote(note);
        ///////////
        if (note.getNoteId()==null){
            //存在
           Long nd = noteMapper.getTheNote(notebook.getNbookId(),note.getNoteName());
           SharedNoteAddress sa = addressMapper.getAddressByNote(userId,nd);
           return new AddressDTO(sa);
        }
        SharedNoteAddress noteAddress = new SharedNoteAddress(note,new User(userId),address);
        int num = addressMapper.insertOneAddress(noteAddress);
        if (num>0) return new AddressDTO(noteAddress);
        else return null;
    }

    public AddressDTO getAddress(Long userId,Long noteId){
        SharedNoteAddress address = addressMapper.getAddressByNote(userId, noteId);
        return address==null?null:new AddressDTO(address);
    }

    @Transactional
    public boolean deleteAddress(Long userId,Long noteId){
        int i = addressMapper.deleteAddress(userId,noteId);
        noteMapper.deleteOne(noteId);
        return i>0;
    }


    public NoteDTO getNoteByAddress(String address){
        Note note = noteMapper.getNoteByAddress(address);
        if (note!=null){
            NoteDTO noteDTO = new NoteDTO(note, ChineseRoot.SHARED.toString());
            noteDTO.setEdit(false);
            return noteDTO;
        }
        return null;
    }
}
