package top.renote.model;

/**
 * Created by Joder_X on 2018/1/12.
 */
public class Interest {
    private Long likeID;
    private Byte types;
    private User user;
    private Note note;

    public Interest() {
    }

    public Interest(Byte types, User user, Note note) {
        this.types = types;
        this.user = user;
        this.note = note;
    }

    public Long getLikeID() {
        return likeID;
    }

    public void setLikeID(Long likeID) {
        this.likeID = likeID;
    }

    public Byte getTypes() {
        return types;
    }

    public void setTypes(Byte types) {
        this.types = types;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }
}
