package com.hesong.mail.model;

public class Protocol {
    
    private String type;
    private String host;
    private String port;
    private String ssl;
    
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public String getPort() {
        return port;
    }
    public void setPort(String port) {
        this.port = port;
    }
    public String getSsl() {
        return ssl;
    }
    public void setSsl(String ssl) {
        this.ssl = ssl;
    }

}
