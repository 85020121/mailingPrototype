package com.hesong.mail.dao;

import com.hesong.mail.model.Mail;

public interface MailDao {
    
    void save(Mail mail);
    void delete(Mail mail);
    Mail findByUID(String uid, String emailAdr);
}
