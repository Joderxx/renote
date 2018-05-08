package top.renote.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import top.renote.model.RegisterUser;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * Created by Joder_X on 2017/12/11.
 * 注册需要接收的信息
 */
public class RegisterUserDTO {
    @NotBlank(message = "账号不能为空")
    @Length(min = 5,max = 20,message = "账号长度为5-20个字符")
    private String account;//账号
    @NotBlank(message = "密码不能为空")
    @Length(min = 6,max = 20,message = "密码长度为6-20个字符")
    private String password;//密码
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不对")
    private String email;//邮箱
    @Range(max = 1,min = 0,message = "性别有误")
    private Byte sex;
    @NotBlank(message = "验证码不能为空")
    private String verificateCode;//验证码


    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
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

    public String getVerificateCode() {
        return verificateCode;
    }

    public void setVerificateCode(String verificateCode) {
        this.verificateCode = verificateCode;
    }

    public RegisterUser toRegisterUser(){
        return new RegisterUser(account,password,email,new Date(),sex);
    }
}
