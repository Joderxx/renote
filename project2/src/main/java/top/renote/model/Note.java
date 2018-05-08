package top.renote.model;

import java.util.Date;

/**
 * Created by Joder_X on 2017/11/26.
 *
 * Table: note
 */
public class Note {

    private Long noteId;//笔记id
    private Boolean delete;//是否删除
    private String noteName;//笔记名字
    private Date gmtCreate;//创建时间
    private Date gmtModified;//最后修改时间
    private NoteContent noteContent;
    public Note() {
    }



    public Note(Boolean delete, String noteName, Date gmtCreate, Date gmtModified, NoteContent noteContent) {
        this.delete = delete;
        this.noteName = noteName;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.noteContent = noteContent;
    }

    public Note(Long noteId,Boolean delete, String noteName, Date gmtCreate, Date gmtModified,NoteContent noteContent) {
        this.delete = delete;
        this.noteName = noteName;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.noteContent = noteContent;
        this.noteId = noteId;
    }

    public Note(Long noteId){
        this.noteId = noteId;
    }

    public NoteContent getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(NoteContent noteContent) {
        this.noteContent = noteContent;
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
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

    @Override
    public String toString() {
        return "Note{" +
                "noteId=" + noteId +
                ", delete=" + delete +
                ", noteName='" + noteName + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", noteContent=" + noteContent +
                '}';
    }
}
