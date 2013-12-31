package com.hesong.bo.impl;

import java.util.List;

import com.hesong.bo.AccountBo;
import com.hesong.dao.AccountDao;
import com.hesong.model.Account;

public class AccountBoImpl implements AccountBo{

    AccountDao accountDao; 
    
    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void save(Account a) {
        this.accountDao.save(a);
    }

    @Override
    public void update(Account a) {
        this.accountDao.update(a);
    }

    @Override
    public void delete(Account a) {
        this.accountDao.delete(a);
    }

    @Override
    public Account findByEmailAdrs(String emailAdrs) {        
        return this.accountDao.findByEmailAdrs(emailAdrs);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List getAllAccount() {
        return this.accountDao.getAllAccount();
    }

    @Override
    public void saveAll(List<Account> list) {
        this.accountDao.saveAll(list);
    }
    
}
