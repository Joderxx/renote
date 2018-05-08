package top.renote.model;

import java.util.Date;

/**
 * Created by Joder_X on 2017/11/26.
 *
 * Table: shared_note_comment
 */
public class SharedNoteComment {

    private Long commentId;//评论id
    private String content;//内容
    private Date gmtCreate;//创建时间
    private Integer like;//喜欢数
    private Integer dislike;//不喜欢数
    private Long parentCommentId;//父评论id
    private User user;//属于用户
    private Note note;//属于笔记

    public SharedNoteComment() {
    }

    public SharedNoteComment(String content, Date gmtCreate, Integer like, Integer dislike, Long parentCommentId, User user, Note note) {
        this.content = content;
        this.gmtCreate = gmtCreate;
        this.like = like;
        this.dislike = dislike;
        this.parentCommentId = parentCommentId;
        this.user = user;
        this.note = note;
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

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

}
