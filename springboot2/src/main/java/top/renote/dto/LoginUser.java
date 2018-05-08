package top.renote.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by Joder_X on 2018/1/19.
 * 登录接收的用户信息
 */
public class LoginUser {
    @NotBlank(message = "账号不能为空")
    @Length(min = 5,max = 20,message = "账号长度为5-20个字符")
    private String account;//账号
    @NotBlank(message = "密码不能为空")
    @Length(min = 6,max = 20,message = "密码长度为6-20个字符")
    private String password;//密码
    private String code;//验证码
    private Boolean select = false;//是否选择保存30天

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getSelect() {
        return select;
    }

    public void setSelect(Boolean select) {
        this.select = select;
    }
}
