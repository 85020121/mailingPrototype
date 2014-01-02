package com.hesong.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hesong.dao.AccountDao;
import com.hesong.mailEngine.tools.MailingLogger;
import com.hesong.model.Account;

public class AccountDaoImpl extends HibernateDaoSupport implements AccountDao {

    @Override
    public void save(Account a) {
        try {
            getHibernateTemplate().save(a);

        } catch (Exception e) {
            MailingLogger.log.error("Svae account error, cuased by: "
                    + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void saveAll(List<Account> list) {
        try {
            getHibernateTemplate().saveOrUpdateAll(list);

        } catch (Exception e) {
            MailingLogger.log.error("Svae account list error, cuased by: "
                    + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void update(Account a) {
        getHibernateTemplate().update(a);
    }

    @Override
    public void delete(Account a) {
        getHibernateTemplate().delete(a);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Account findByEmailAdrs(String emailAdrs) {
        List list = getHibernateTemplate().find(
                "from Account a where a.account=?", emailAdrs);
        return list.size() > 0 ? (Account) list.get(0) : null;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List getAllAccount() {
        List list = getHibernateTemplate().find("from Account");
        return list;
    }

}
