package top.renote.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by Joder_X on 2018/3/11.
 */
public class LoginDTO {

    private String account;
    private String password;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;

    public LoginDTO() {
    }

    public LoginDTO(String account, String password, Date time) {
        this.account = account;
        this.password = password;
        this.time = time;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", time=" + time +
                '}';
    }
}
