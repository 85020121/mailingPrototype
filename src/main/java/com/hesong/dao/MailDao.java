package com.hesong.dao;

import java.util.List;

import com.hesong.model.Mail;

public interface MailDao {
    
    void save(Mail mail);
    void save(List<Mail> list);
    void delete(Mail mail);
    @SuppressWarnings("rawtypes")
    List findByUnitIDandEmail(String unitID, String emailAdr);
    @SuppressWarnings("rawtypes")
    List getUIDList(String emailAdr);
}
