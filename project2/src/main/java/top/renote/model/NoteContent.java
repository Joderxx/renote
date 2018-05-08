package top.renote.model;

/**
 * Created by Joder_X on 2017/11/28.
 *
 * Table: note_content
 *
 */
public class NoteContent {

    private Long ncId;//Id
    private String content;//内容
    private String tag;//标签 格式应为 java,c,c++
    private Long createId;
    private Boolean shared;
    private String type;

    public NoteContent() {
    }

    public NoteContent(String content, String tag, Long createId, Boolean shared,String type) {
        this.content = content;
        this.tag = tag;
        this.createId = createId;
        this.shared = shared;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getNcId() {
        return ncId;
    }

    public void setNcId(Long ncId) {
        this.ncId = ncId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Boolean getShared() {
        return shared;
    }

    public void setShared(Boolean shared) {
        this.shared = shared;
    }

    @Override
    public String toString() {
        return "NoteContent{" +
                "ncId=" + ncId +
                ", content='" + content + '\'' +
                ", tag='" + tag + '\'' +
                ", createId=" + createId +
                ", shared=" + shared +
                ", type='" + type + '\'' +
                '}';
    }
}
