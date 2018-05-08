package top.renote.model;

import java.util.Date;

/**
 * Created by Joder_X on 2018/2/27.
 * table: ratings
 */
public class Rating {
    private Double preference;
    private Long createTime;
    private Long userId;
    private Long noteId;

    public Rating() {
    }

    public Rating(Double preference, Long createTime, Long userId, Long noteId) {
        this.preference = preference;
        this.createTime = createTime;
        this.userId = userId;
        this.noteId = noteId;
    }

    public Double getPreference() {
        return preference;
    }

    public void setPreference(Double preference) {
        this.preference = preference;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }
}
