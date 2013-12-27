package com.hesong.mailingPrototype;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import sun.net.ftp.FtpProtocolException;

import com.hesong.mail.bo.MailBo;
import com.hesong.mail.model.Email;
import com.hesong.mail.model.Mail;
import com.hesong.mailEngine.ftp.POP3FTPclient;
import com.hesong.mailEngine.pop.POP3;
import com.hesong.mailEngine.tools.MailingLogger;

public class POP3Test {
    
    Email e;
    List<String> uidList;
    FTPClient ftp;
    
    @Before
    public void testSetup() throws IOException {
        e = new Email();
        e.setAccount("bowen_test11@163.com");
        e.setPassword("waiwai33");
        e.setReceiveServer("pop3.163.com");
        
        uidList = new ArrayList<>();
        
        ftp = POP3FTPclient.getFTPclientConnection("127.0.0.1", 21, "bowen", "waiwai");
    }

    @After
    public void testCleanup() throws IOException {
      // Teardown for data used by the unit tests
        POP3FTPclient.disconnect(ftp);
    }

    @Test
    public void testInboxCount() throws MessagingException, IOException {
        Email e = new Email();
        e.setAccount("bowen_test11@163.com");
        e.setPassword("waiwai33");
        e.setReceiveServer("pop3.163.com");

        assertTrue(POP3.getInboxCount(e) == 5);
    }

    @Test
    public void databaseTest() throws MessagingException, IOException {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");
        
        MailBo mailBo = (MailBo)appContext.getBean("mailBo");
                
        List<Mail> list = POP3.getInboxMessages(e, uidList, ftp);
        MailingLogger.log.info(list.get(0));
        mailBo.save(list.get(0));
    }
    
    @Test
    public void ftpTest() throws IOException, MessagingException{
        
        POP3.getInboxMessages(e, uidList, ftp);
        assertTrue(true);
    }

    @Test
    public void ftpMkdirTest() throws IOException, FtpProtocolException{
        
        POP3FTPclient.mkdir(ftp, "/mkdir_test/ttt/bbbbbbbb/asada/aw/a/adas");
        assertTrue(true);
    }
}
