package com.hesong.model;

import java.io.Serializable;
import java.util.Date;

public class Mail implements Serializable{

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String uid;
    private String unitID;
    private String sender;
    private String receiver;
    private String subject;
    private Date sentDate;
    private int size;
    private String content;
    private String attachmtDir;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getUnitID() {
        return unitID;
    }
    public void setUnitID(String unitID) {
        this.unitID = unitID;
    }
    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public String getReceiver() {
        return receiver;
    }
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public Date getSentDate() {
        return sentDate;
    }
    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getAttachmtDir() {
        return attachmtDir;
    }
    public void setAttachmtDir(String attachmtDir) {
        this.attachmtDir = attachmtDir;
    }

}
