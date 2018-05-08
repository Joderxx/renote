package top.renote.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import top.renote.model.User;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Joder_X on 2018/1/15.
 * 成功登录后返回给浏览器的信息
 */
public class SuccessUser implements Serializable {
    private String account;
    private String username;
    private String avatar;
    private String email;
    private Byte sex;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date lastLastLogin;

    public SuccessUser(){}

    public SuccessUser(User user){
        transfer(user);
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Date getLastLastLogin() {
        return lastLastLogin;
    }

    public void setLastLastLogin(Date lastLastLogin) {
        this.lastLastLogin = lastLastLogin;
    }

    public void transfer(User user){
        if (user==null)return;
        this.account = user.getAccount();
        this.username = user.getUserName();
        this.sex = user.getSex();
        this.lastLastLogin = user.getGmtLastLogin();
        this.avatar = user.getAvatar();
        this.email = user.getEmail();
    }

}
