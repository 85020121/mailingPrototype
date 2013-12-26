package com.hesong.mail.bo.impl;

import com.hesong.mail.bo.MailBo;
import com.hesong.mail.dao.MailDao;
import com.hesong.mail.model.Mail;

public class MailBoImpl implements MailBo{
    
    MailDao mailDao;

    public void setMailDao(MailDao mailDao) {
        this.mailDao = mailDao;
    }

    public void save(Mail mail) {
        this.mailDao.save(mail);
    }

    public void delete(Mail mail) {
        mailDao.delete(mail);
    }

    public Mail findByUID(String uid, String emailAdr) {
        return mailDao.findByUID(uid, emailAdr);
    }

}
