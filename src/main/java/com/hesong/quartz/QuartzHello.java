package com.hesong.quartz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.commons.net.ftp.FTPClient;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hesong.mail.model.Email;
import com.hesong.mailEngine.ftp.POP3FTPclient;
import com.hesong.mailEngine.pop.POP3;

public class QuartzHello implements Job {

    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        // TODO Auto-generated method stub
        Email e = new Email();
        e.setAccount("bowen_test11@163.com");
        e.setPassword("waiwai33");
        e.setReceiveServer("pop3.163.com");
        
        List<String> uidList = new ArrayList<>();
        
        try {
            FTPClient ftp = POP3FTPclient.getFTPclientConnection("127.0.0.1", 21, "bowen", "waiwai");
            POP3.getInboxMessages(e, uidList, ftp);
            POP3FTPclient.disconnect(ftp);
            
        } catch (IOException | MessagingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

}
