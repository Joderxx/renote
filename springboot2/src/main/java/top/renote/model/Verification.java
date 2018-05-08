package top.renote.model;

import java.util.Date;

/**
 * Created by Joder_X on 2017/11/26.
 *
 * Table: verification
 * 验证信息，可用于修改密码
 */
public class Verification {

    private Long verificationId;
    private Long userId;
    private User user;//哪个用户
    private String verificationCode;//验证码
    private Date gmtCreate;//创建时间

    public Long getVerificationId() {
        return verificationId;
    }

    public void setVerificationId(Long verificationId) {
        this.verificationId = verificationId;
    }

    public User getUser() {
        return user;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Override
    public String toString() {
        return "Verification{" +
                "verificationId=" + verificationId +
                ", userId=" + userId +
                ", user=" + user +
                ", verificationCode='" + verificationCode + '\'' +
//                ", gmtCreate=" + gmtCreate +
                '}';
    }
}
