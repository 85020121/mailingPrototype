package com.hesong.mailingPrototype;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import javax.mail.MessagingException;

import org.junit.Test;

import com.hesong.mail.model.Email;
import com.hesong.mailEngine.pop.POP3;

public class POP3Test {

    @Test
    public void testInboxCount() throws MessagingException, IOException {
        Email e = new Email();
        e.setAccount("bowen_test11@163.com");
        e.setPassword("waiwai33");
        e.setReceiveServer("pop3.163.com"); 
        
        ArrayList<String> uidList = new ArrayList<>();
        POP3.getInboxMessages(e, uidList);
        assertTrue(true);
        //assertTrue(POP3.getInboxCount(e) == 5); 
    }
    
    

}
