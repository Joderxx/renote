package top.renote.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Joder_X on 2017/11/26.
 *
 * Table : user
 */
public class User implements Serializable {
    private Long userId;//用户id
    private String userName;//用户名字
    private Byte sex;//用户性别 ，男1，女2,无0
    private String avatar;//用户头像
    private String account;//用户账号
    private String password;//用户密码
    private String email;//邮箱
    private Date gmtLastLogin;//最后登录时间
    private Date gmtCreate;//账号创建时间
    private Date gmtModified;//最后修改时间


    public User() {
    }

    public User(Long userId) {
        this.userId = userId;
    }

    public User( String userName, Byte sex, String avatar,
                String account, String password,
                String email, Date gmtLastLogin,
                Date gmtCreate, Date gmtModified) {
        this.userName = userName;
        this.sex = sex;
        this.avatar = avatar;
        this.account = account;
        this.password = password;
        this.email = email;
        this.gmtLastLogin = gmtLastLogin;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getGmtLastLogin() {
        return gmtLastLogin;
    }

    public void setGmtLastLogin(Date gmtLastLogin) {
        this.gmtLastLogin = gmtLastLogin;
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
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", sex=" + sex +
                ", avatar='" + avatar + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", gmtLastLogin=" + gmtLastLogin +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                '}';
    }
}
