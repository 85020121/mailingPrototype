package com.hesong.mailingPrototype;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hesong.mail.bo.MailBo;
import com.hesong.mail.model.Email;
import com.hesong.mail.model.Mail;
import com.hesong.mailEngine.pop.POP3;

public class POP3Test {

//    @Test
//    public void testInboxCount() throws MessagingException, IOException {
//        Email e = new Email();
//        e.setAccount("bowen_test11@163.com");
//        e.setPassword("waiwai33");
//        e.setReceiveServer("pop3.163.com");
//
//        ArrayList<String> uidList = new ArrayList<>();
//        POP3.getInboxMessages(e, uidList);
//        assertTrue(true);
//        // assertTrue(POP3.getInboxCount(e) == 5);
//    }

    @Test
    public void databaseTest() throws MessagingException, IOException {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");
        
        MailBo mailBo = (MailBo)appContext.getBean("mailBo");
        
        Email e = new Email();
        e.setAccount("bowen_test11@163.com");
        e.setPassword("waiwai33");
        e.setReceiveServer("pop3.163.com");
        
        List<Mail> list = POP3.getInboxMessages(e, new ArrayList<String>());
        POP3.log.info(list.get(0));
        mailBo.save(list.get(0));
    }

}
