package top.renote.model;

import java.util.Date;

/**
 * Created by Joder_X on 2018/2/26.
 * table: login
 */
public class Login {
    private Long loginId;
    private String ip;
    private Date gmtCreate;
    private User user;

    public Login() {
    }

    public Login(String ip, Date gmtCreate, User user) {
        this.ip = ip;
        this.gmtCreate = gmtCreate;
        this.user = user;
    }

    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
