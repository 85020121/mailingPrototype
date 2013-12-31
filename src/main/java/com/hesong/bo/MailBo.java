package com.hesong.bo;

import java.util.List;

import com.hesong.model.Mail;

public interface MailBo {
    
    void save(Mail mail);
    void save(List<Mail> list);
    void delete(Mail mail);
    @SuppressWarnings("rawtypes")
    List findByUnitIDandEmail(String uid, String emailAdr);
    @SuppressWarnings("rawtypes")
    List getUIDList(String emailAdr);
}
