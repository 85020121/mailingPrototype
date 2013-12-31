package com.hesong.model;

import java.io.Serializable;
import java.util.List;

public class Account implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String sendServer;
    private String receiveServer;
    private String account;
    private String password;
    private char ssl;  // 1: SLL is enable  0: not
    int interval;
    private String remark;
    private String unitID;
    private List<String> uidList;
    


    public Account() {
    }
    
    public Account(String account, String password, String receiveServer) {
        super();
        this.account = account;
        this.password = password;
        this.receiveServer = receiveServer;
        this.setId("aavvaa");
        this.ssl = '0';
        this.setUnitID("asdsaaaaaa");
        this.setSendServer("asdasdas");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    
    public List<String> getUidList() {
        return uidList;
    }

    public void setUidList(List<String> uidList) {
        this.uidList = uidList;
    }
}
