package com.hesong.mail.bo;

import com.hesong.mail.model.Mail;

public interface MailBo {
    
    void save(Mail mail);
    void delete(Mail mail);
    Mail findByUID(String uid, String emailAdr);
}
