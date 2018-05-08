package top.renote.model;

import java.util.Date;

/**
 * Created by Joder_X on 2018/2/9.
 * 表：rmb
 */
public class Rmb {
    private String rmbHashId;
    private String rmbUUID;
    private Long userId;
    private Date gmtCreate;

    public Rmb(){

    }

    public Rmb(String rmbHashId, String rmbUUID, Long userId, Date gmtCreate) {
        this.rmbHashId = rmbHashId;
        this.rmbUUID = rmbUUID;
        this.userId = userId;
        this.gmtCreate = gmtCreate;
    }

    public String getRmbHashId() {
        return rmbHashId;
    }

    public void setRmbHashId(String rmbHashId) {
        this.rmbHashId = rmbHashId;
    }

    public String getRmbUUID() {
        return rmbUUID;
    }

    public void setRmbUUID(String rmbUUID) {
        this.rmbUUID = rmbUUID;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}
