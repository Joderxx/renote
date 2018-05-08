package top.renote.model;

import java.util.Date;

/**
 * Created by Joder_X on 2018/4/17.
 * table : advice通知
 */
public class Advice {

    private Long adviceId;
    private Long userId;
    private Long noteId;
    private Long commentId;
    private Long uId;
    private Long cId;
    private Date gmtCreate;
    private String type;
    private Boolean flag=false;//是否通知了 true通知,false未通知

    public Advice() {
    }

    public Advice(Long userId, Long noteId, Long commentId, Long uId, Long cId, String type) {
        this.userId = userId;
        this.noteId = noteId;
        this.commentId = commentId;
        this.uId = uId;
        this.cId = cId;
        this.type = type;
    }

    public Long getAdviceId() {
        return adviceId;
    }

    public void setAdviceId(Long adviceId) {
        this.adviceId = adviceId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
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

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
}
