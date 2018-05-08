package top.renote.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Joder_X on 2017/11/26.
 *
 * Table: register
 * 注册表，临时存储注册信息
 */
public class RegisterUser implements Serializable {
    private String account;//账号
    private String password;//密码
    private String email;//邮箱
    private String code;//验证码
    private Byte sex;//性别{0:男,1:女}

    public RegisterUser() {
    }

    public RegisterUser(String account, String password, String email, Date gmtCreate,  Byte sex) {
        this.account = account;
        this.password = password;
        this.email = email;
        this.sex = sex;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    @Override
    public String toString() {
        return "RegisterUser{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", code='" + code + '\'' +
                ", sex=" + sex +
                '}';
    }
}
