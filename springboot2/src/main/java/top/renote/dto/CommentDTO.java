package top.renote.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import top.renote.model.SharedNoteComment;

import java.util.Date;

/**
 * Created by Joder_X on 2018/1/21.
 */
public class CommentDTO {

    private Long commentId;//评论id
    private String content;//内容
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date gmtCreate;//创建时间
    private Integer like;//喜欢数
    private Integer dislike;//不喜欢数
    private Integer childCount = 0;
    private String username;//用户名
    private String avatar;//头像
    private Long noteId;
    private String noteName;
    private String parId;//父评论Id或笔记Id “note_","comment_"

    public CommentDTO(SharedNoteComment comment) {
        transfer(comment);
    }

    public CommentDTO(SharedNoteComment comment,Integer childCount) {
        transfer(comment);
        this.childCount=childCount;
    }

    public CommentDTO() {
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public String getParId() {
        return parId;
    }

    public void setParId(String parId) {
        this.parId = parId;
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

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public Integer getDislike() {
        return dislike;
    }

    public void setDislike(Integer dislike) {
        this.dislike = dislike;
    }

    public Integer getChildCount() {
        return childCount;
    }

    public void setChildCount(Integer childCount) {
        this.childCount = childCount;
    }

    public void transfer(SharedNoteComment comment){
        if (comment==null)return;
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.like = comment.getLike();
        this.dislike = comment.getDislike();
        this.gmtCreate = comment.getGmtCreate();
        this.username = comment.getUser().getUserName();
        this.avatar = comment.getUser().getAvatar();
        this.noteId = comment.getNote().getNoteId();
        this.noteName = comment.getNote().getNoteName();
        if (comment.getParentCommentId()!=0){
            parId = "comment_"+comment.getParentCommentId();
        }else parId = "note_"+comment.getNote().getNoteId();
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
                "commentId=" + commentId +
                ", content='" + content + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", like=" + like +
                ", dislike=" + dislike +
                ", childCount=" + childCount +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", parId='" + parId + '\'' +
                '}';
    }
}
