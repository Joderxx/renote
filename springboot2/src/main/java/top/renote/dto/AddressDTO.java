package top.renote.dto;

import top.renote.model.SharedNoteAddress;

/**
 * Created by pokedo on 2018/1/30.
 */
public class AddressDTO {
    private Long sharedAddressId;//分享id
    private Long noteId;//笔记id
    private String noteAddress;//分享地址

    public AddressDTO(SharedNoteAddress sharedNoteAddress){
        transfer(sharedNoteAddress);
    }

    public AddressDTO(){}

    public Long getSharedAddressId() {
        return sharedAddressId;
    }

    public void setSharedAddressId(Long sharedAddressId) {
        this.sharedAddressId = sharedAddressId;
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public String getNoteAddress() {
        return noteAddress;
    }

    public void setNoteAddress(String noteAddress) {
        this.noteAddress = noteAddress;
    }

    public void transfer(SharedNoteAddress sharedNoteAddress){
        if (sharedNoteAddress==null)return;
        this.sharedAddressId = sharedNoteAddress.getSharedAddressId();
        this.noteId = sharedNoteAddress.getNote().getNoteId();
        this.noteAddress = sharedNoteAddress.getNoteAddress();
    }

}
