package com.hesong.bo.impl;

import java.util.List;

import com.hesong.bo.MailBo;
import com.hesong.dao.MailDao;
import com.hesong.model.Mail;

public class MailBoImpl implements MailBo {

    MailDao mailDao;

    public void setMailDao(MailDao mailDao) {
        this.mailDao = mailDao;
    }

    public void save(Mail mail) {
        this.mailDao.save(mail);
    }

    public void save(List<Mail> list) {
        this.mailDao.save(list);
    }

    public void delete(Mail mail) {
        mailDao.delete(mail);
    }

    @SuppressWarnings("rawtypes")
    public List findByUnitIDandEmail(String uid, String emailAdr) {
        return mailDao.findByUnitIDandEmail(uid, emailAdr);
    }

    @SuppressWarnings("rawtypes")
    public List getUIDList(String emailAdr) {
        return mailDao.getUIDList(emailAdr);
    }

}
