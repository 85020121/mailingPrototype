package com.hesong.bo;

import java.util.List;

import com.hesong.model.Account;

public interface AccountBo {

    void save(Account a);
    void update(Account a);
    void delete(Account a);
    Account findByEmailAdrs(String emailAdrs);
    @SuppressWarnings("rawtypes")
    List getAllAccount();
    void saveAll(List<Account> list);
}
