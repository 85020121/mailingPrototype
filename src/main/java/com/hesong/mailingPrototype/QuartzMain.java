package com.hesong.mailingPrototype;

import java.io.IOException;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hesong.bo.AccountBo;
import com.hesong.bo.MailBo;
import com.hesong.mailEngine.ftp.POP3FTPclient;
import com.hesong.mailEngine.tools.MailingLogger;
import com.hesong.model.Account;
import com.hesong.quartz.PullingMail;

public class QuartzMain {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws SchedulerException, IOException {
        FTPClient ftp = POP3FTPclient.getFTPclientConnection("127.0.0.1", 21, "bowen",
                "waiwai");

        ApplicationContext appContext = new ClassPathXmlApplicationContext(
                "spring/config/BeanLocations.xml");
        
        AccountBo accountBo = (AccountBo) appContext.getBean("accountBo");
        List<Account> list = (List<Account>)accountBo.getAllAccount();        
        MailBo mailBo = (MailBo) appContext.getBean("mailBo");
        
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        
        for(Account a : list){
            String jobID = a.getAccount(); 
            MailingLogger.log.info("JobID is:" + jobID);
            a.setUidList(mailBo.getUIDList(jobID));
            JobDetail job = JobBuilder.newJob(PullingMail.class).withIdentity(jobID, "group1").build();
            job.getJobDataMap().put(PullingMail.ACCOUNT_FLAG, a);
            job.getJobDataMap().put(PullingMail.FTP_FLAG, ftp);
            job.getJobDataMap().put(PullingMail.MAIL_SAVER_FLAG, mailBo);
            
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity(jobID, "group1")
                    .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(a.getInterval()).repeatForever())
                    .build();
            // Add job to scheduler
            scheduler.scheduleJob(job, trigger);
        }
   
        scheduler.start();

    }

}
