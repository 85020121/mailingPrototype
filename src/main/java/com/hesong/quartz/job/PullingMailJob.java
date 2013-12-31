package com.hesong.quartz.job;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.commons.net.ftp.FTPClient;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hesong.bo.MailBo;
import com.hesong.mailEngine.pop.POP3;
import com.hesong.mailEngine.tools.MailingLogger;
import com.hesong.model.Account;
import com.hesong.model.Mail;

public class PullingMailJob implements Job {

    public static final String ACCOUNT_FLAG = "account";
    public static final String FTP_FLAG = "ftp";
    public static final String MAIL_SAVER_FLAG = "mailSaver";

    @SuppressWarnings("unchecked")
    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {

        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        Account account = (Account) dataMap.get(ACCOUNT_FLAG);
        FTPClient ftp = (FTPClient) dataMap.get(FTP_FLAG);
        MailBo mailBo = (MailBo)dataMap.get(MAIL_SAVER_FLAG);
        account.setUidList((ArrayList<String>)mailBo.getUIDList(account.getAccount()));
        
        List<Mail> inbox;
        try {
            
            inbox = POP3.getInboxMessages(account, ftp);
            if (inbox.size()>0) {
                mailBo.save(inbox);
                MailingLogger.log.info(inbox.size()+" mails received.");;
            } else {
                MailingLogger.log.info("No more new mails.");
            }

        } catch (IOException | MessagingException e) {
            MailingLogger.log.info("Exception message: " + e.toString());
            MailingLogger.log.info("Caused by account: "
                    + account.getAccount());
            e.printStackTrace();
            return;
        }

        
    }
}
