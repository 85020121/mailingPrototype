package com.hesong.dao;

import java.util.List;

import com.hesong.model.Account;

public interface AccountDao {
    
    void save(Account a);
    void update(Account a);
    void delete(Account a);
    Account findByEmailAdrs(String emailAdrs);
    @SuppressWarnings("rawtypes")
    List getAllAccount();
    void saveAll(List<Account> list);

}
