package top.renote.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by Joder_X on 2018/4/17.
 */
public class AdviceDTO {

    private String username;//触发者名
	private String noteName;
    private String avatar;//触发者头像
	private Long noteId;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date gmtCreate;
	private String type;  //note  comment  likenote  likecomment
	
    private String  c; //触发事件的评论
	private String comment;//作者评论




    public AdviceDTO() {
    }

    public AdviceDTO(Long noteId, String username, String avatar, Date gmtCreate, String type) {
        this.noteId = noteId;
        this.username = username;
        this.avatar = avatar;
        this.gmtCreate = gmtCreate;
        this.type = type;
    }

    public AdviceDTO(Long noteId, String noteName, String comment, String username, String avatar, String c, Date gmtCreate, String type) {
        this.noteId = noteId;
        this.noteName = noteName;
        this.comment = comment;
        this.username = username;
        this.avatar = avatar;
        this.c = c;
        this.gmtCreate = gmtCreate;
        this.type = type;
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
