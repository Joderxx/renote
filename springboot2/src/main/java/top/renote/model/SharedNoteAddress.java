package top.renote.model;

/**
 * Created by Joder_X on 2017/11/26.
 * <p>
 * Table: shared_note_address
 */
public class SharedNoteAddress {

    private Long sharedAddressId;
    private Note note;//属于笔记
    private User user;//用户
    private String noteAddress;//分享地址

    public SharedNoteAddress() {
    }

    public SharedNoteAddress(Note note, User user, String noteAddress) {
        this.note = note;
        this.user = user;
        this.noteAddress = noteAddress;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getSharedAddressId() {
        return sharedAddressId;
    }

    public void setSharedAddressId(Long sharedAddressId) {
        this.sharedAddressId = sharedAddressId;
    }


    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public String getNoteAddress() {
        return noteAddress;
    }

    public void setNoteAddress(String noteAddress) {
        this.noteAddress = noteAddress;
    }

    @Override
    public String toString() {
        return "SharedNoteAddress{" +
                "sharedAddressId=" + sharedAddressId +
                ", note=" + note +
                ", user=" + user +
                ", noteAddress='" + noteAddress + '\'' +
                '}';
    }

}
