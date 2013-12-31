package com.hesong.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hesong.dao.MailDao;
import com.hesong.model.Mail;

public class MailDaoImpl extends HibernateDaoSupport implements MailDao {

    public void save(Mail mail) {
        getHibernateTemplate().save(mail);
    }

    public void save(List<Mail> list) {
        try {
            getHibernateTemplate().saveOrUpdateAll(list);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Mail mail) {
        getHibernateTemplate().delete(mail);
    }

    @SuppressWarnings("rawtypes")
    public List findByUnitIDandEmail(String unitID, String emailAdr) {
        List list = getHibernateTemplate().find(
                String.format(
                        "from Mail m where m.unitID='%s' and m.receiver='%s'",
                        unitID, emailAdr));
        return list;
    }

    @SuppressWarnings("rawtypes")
    public List getUIDList(String emailAdr) {
        List list = getHibernateTemplate().find(
                "select m.uid from Mail m where m.receiver=?", emailAdr);
        return list;
    }

}
