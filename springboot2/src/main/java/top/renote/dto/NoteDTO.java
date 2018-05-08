package top.renote.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.util.StringUtil;
import top.renote.model.Note;
import top.renote.util.digestUtil.MyStringUtil;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by Joder_X on 2018/1/21.
 */
public class NoteDTO {

    private Long noteId;//笔记id
    private String content;//内容
    private String[] tag;//标签，输入输出必须通过转换，arrayToStriing（）和stringToArray（）
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date gmtCreate;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date gmtModified;//最后修改时间
    private String type;
    private Boolean edit = true;//是否可修改,default = true
    private String noteName;//笔记名字
    private String path;//路径

    public NoteDTO(Note note) {
        transfer(note);
    }

    public NoteDTO(Note note,String par){
        transfer(note,par);
    }

    public NoteDTO() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getTag() {
        return tag;
    }

    public void setTag(String[] tag) {
        this.tag = tag;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Boolean getEdit() {
        return edit;
    }

    public void setEdit(Boolean edit) {
        this.edit = edit;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public void transfer(Note note){
        if (note==null)return;
        this.content = note.getNoteContent().getContent();
        this.gmtCreate = note.getGmtCreate();
        this.noteId = note.getNoteId();
        this.gmtModified = note.getGmtModified();
        this.tag = MyStringUtil.stringToArray(note.getNoteContent().getTag());
        this.noteName = note.getNoteName();
        this.type = note.getNoteContent().getType();
    }

    public void transfer(Note note,String par){
        transfer(note);
        if (note!=null&& !StringUtil.isEmpty(par)){
            setPath(par+"/"+noteName);
        }
    }

    @Override
    public String toString() {
        return "NoteDTO{" +
                "noteId=" + noteId +
                ", content='" + content + '\'' +
                ", tag=" + Arrays.toString(tag) +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", type='" + type + '\'' +
                ", edit=" + edit +
                ", noteName='" + noteName + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
