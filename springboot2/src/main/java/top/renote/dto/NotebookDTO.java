package top.renote.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.util.StringUtil;
import top.renote.model.Notebook;

import java.util.Date;

/**
 * Created by Joder_X on 2018/1/21.
 */
public class NotebookDTO {
    private Long nbookId;//笔记本id
    private String nbookName;//笔记本名字
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date gmtCreate;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date gmtModified;//最后修改时间
    private String path;//路径

    public NotebookDTO(Notebook notebook) {
        transfer(notebook);
    }

    public NotebookDTO(Notebook notebook,String par){
        transfer(notebook,par);
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void transfer(Notebook notebook){
        if (notebook==null)return;
        this.nbookId = notebook.getNbookId();
        this.nbookName = notebook.getNbookName();
        this.gmtCreate = notebook.getGmtCreate();
        this.gmtModified = notebook.getGmtModified();
    }

    public void transfer(Notebook notebook,String par){
        transfer(notebook);
        if (notebook!=null&& !StringUtil.isEmpty(par)){
            setPath(par+"/"+nbookName);
        }
    }


}
