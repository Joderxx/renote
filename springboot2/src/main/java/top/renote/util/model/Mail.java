package top.renote.util.model;

/**
 * Created by Joder_X on 2017/10/13.
 */
public class Mail {
    private String from;
    private String to;
    private String subject;
    private String content;
    private String url;
    private String nickname;

    public Mail(String from, String to, String subject, String content, String url, String nickname) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.content = content;
        this.url = url;
        this.nickname = nickname;
    }

    public Mail() {

    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
