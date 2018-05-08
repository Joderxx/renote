package top.renote.model;

import java.util.Date;
import java.util.List;

/**
 * Created by Joder_X on 2017/11/26.
 *
 * Table: nbook
 */
public class Notebook {
    private Long nbookId;//笔记本id
    private String nbookName;//笔记本名字
    private Date gmtCreate;//创建时间
    private Date gmtModified;//最后修改时间
    private User user;//属于用户
    private Root root;//属于根目录
    private Boolean delete;
    List<Note> notes;//有哪些子笔记

    public Notebook() {
    }

    public Notebook(Long nbookId) {
        this.nbookId = nbookId;
    }

    public Notebook(Long nbookId, String nbookName, Date gmtCreate, Date gmtModified, User user, Root root, Boolean delete) {
        this.nbookId = nbookId;
        this.nbookName = nbookName;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.user = user;
        this.root = root;
        this.delete = delete;
    }

    public Notebook(String nbookName, Date gmtCreate, Date gmtModified, User user, Root root, Boolean delete) {
        this.nbookName = nbookName;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.user = user;
        this.root = root;
        this.delete = delete;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public Root getRoot() {
        return root;
    }

    public void setRoot(Root root) {
        this.root = root;
    }

    public Long getNbookId() {
        return nbookId;
    }

    public void setNbookId(Long nbookId) {
        this.nbookId = nbookId;
    }

    public String getNbookName() {
        return nbookName;
    }

    public void setNbookName(String nbookName) {
        this.nbookName = nbookName;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Notebook{" +
                "nbookId=" + nbookId +
                ", nbookName='" + nbookName + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", delete=" + delete +
                '}';
    }
}
