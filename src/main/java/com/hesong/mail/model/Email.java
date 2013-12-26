package com.hesong.mail.model;

public class Email {
    
    private String account;
    private String password;
    private String sendServer;
    private String receiveServer;
    private char ssl;  // 1: SLL is enable  0: not
    int interval;
    private String remark;
    private String unitID;
    
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
    public String getSendServer() {
        return sendServer;
    }
    public void setSendServer(String sendServer) {
        this.sendServer = sendServer;
    }
    public String getReceiveServer() {
        return receiveServer;
    }
    public void setReceiveServer(String receiveServer) {
        this.receiveServer = receiveServer;
    }
    public char getSsl() {
        return ssl;
    }
    public void setSsl(char ssl) {
        this.ssl = ssl;
    }
    public int getInterval() {
        return interval;
    }
    public void setInterval(int interval) {
        this.interval = interval;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getUnitID() {
        return unitID;
    }
    public void setUnitID(String unitID) {
        this.unitID = unitID;
    }
}
