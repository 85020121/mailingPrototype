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
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hesong.bo.AccountBo;
import com.hesong.bo.MailBo;
import com.hesong.mailEngine.ftp.POP3FTPclient;
import com.hesong.mailEngine.pop.POP3;
import com.hesong.mailEngine.tools.MailingLogger;
import com.hesong.model.Account;
import com.hesong.model.Mail;

public class POP3Test {

    Account a;
    List<String> uidList;
    FTPClient ftp;
    ApplicationContext appContext;
    MailBo mailBo;
    AccountBo accountBo;

    @SuppressWarnings("unchecked")
    @Before
    public void testSetup() throws IOException {

        //a = new Account("bowen_test11@163.com", "waiwai33", "pop3.163.com");
        // a = new Email("test@koyoo.cn", "test123456", "125.93.53.89");

        ftp = POP3FTPclient.getFTPclientConnection("127.0.0.1", 21, "bowen",
                "waiwai");

        appContext = new ClassPathXmlApplicationContext(
                "spring/config/BeanLocations.xml");
        accountBo = (AccountBo) appContext.getBean("accountBo");
        a = (Account)accountBo.getAllAccount().get(0);
        
        mailBo = (MailBo) appContext.getBean("mailBo");
        a.setUidList((ArrayList<String>)mailBo.getUIDList(a.getAccount()));
        
    }

    @After
    public void testCleanup() throws IOException {
        // Teardown for data used by the unit tests
        POP3FTPclient.disconnect(ftp);
        ((ConfigurableApplicationContext) appContext).close();
    }

    @Test
    public void testInboxCount() throws MessagingException, IOException {

        assertTrue(POP3.getInboxCount(a) == 4);
    }

    @Test
    public void getUIDlistTest() throws MessagingException, IOException {

        @SuppressWarnings("rawtypes")
        List list = mailBo.getUIDList(a.getAccount());
        MailingLogger.log.info("LIST: " + list);
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void findByUnitIDandEmailTest() {
        List list = mailBo.findByUnitIDandEmail("1", a.getAccount());
        MailingLogger.log.info("LIST: " + list);
    }

    @Test
    public void databaseTest() throws MessagingException, IOException {

        List<Mail> list = POP3.getInboxMessages(a, ftp);
        mailBo.save(list);
    }

    @Test
    public void ftpTest() throws IOException, MessagingException {

        POP3.getInboxMessages(a, ftp);
        assertTrue(true);
    }

    
    @Test
    public void accountDBTest(){
        Account a = new Account("test", "test", "test");
        a.setId("asdasa");
        a.setInterval(45);
        a.setSendServer("eexxaa");
        a.setSsl('1');
        a.setUnitID("asdaaaasd");
        a.setRemark("测试");
       // accountBo.save(a);
        
        Account a2 = new Account("qaaaa", "asqqwq", "asdsad");
        
        List<Account> list = new ArrayList<>();
        list.add(a);
        list.add(a2);
        accountBo.saveAll(list);
        assertTrue(true);
    }
}
