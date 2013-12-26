package com.hesong.factories;

import java.util.Properties;

import com.hesong.mail.model.Email;


public class PropertiesFactory {

    public static Properties POP3Factory(Email e) {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "pop3");
        props.setProperty("mail.pop3.host", e.getReceiveServer());
        //props.setProperty("mail.pop3.port", "");
        if(e.getSsl()==1)
            props.setProperty("mail.pop3.ssl.enable", "true");
        return props;
    }
  
}
