package com.hesong.mail.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hesong.mail.dao.MailDao;
import com.hesong.mail.model.Mail;

public class MailDaoImpl extends HibernateDaoSupport implements MailDao{

    public void save(Mail mail) {
        getHibernateTemplate().save(mail);
    }

    public void delete(Mail mail) {
        getHibernateTemplate().delete(mail);
    }

    public Mail findByUID(String uid, String emailAdr) {
        @SuppressWarnings("rawtypes")
        List list = getHibernateTemplate().find(String.format("from cc_mail_t_inbox where UID=? Receiver=?", uid, emailAdr));
        return (Mail)list.get(0);
    }
    
    
}
