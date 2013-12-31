package com.hesong.mailingPrototype;

import java.io.IOException;
import java.util.Date;
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
import com.hesong.mailEngine.ftp.factories.FTPConnectionFactory;
import com.hesong.mailEngine.tools.MailingLogger;
import com.hesong.model.Account;
import com.hesong.quartz.job.PullingMailJob;

public class QuartzMain {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws SchedulerException,
            IOException {
        FTPConnectionFactory.initDefualtFTPclientConnection("127.0.0.1", 21,
                "bowen", "waiwai");
        FTPClient ftp = FTPConnectionFactory.getDefaultFTPConnection();
//        FTPClient ftp = FTPConnectionFactory.getFTPClientConnection(
//                "127.0.0.1", 21, "bowen", "waiwai");

        ApplicationContext appContext = new ClassPathXmlApplicationContext(
                "spring/config/BeanLocations.xml");

        AccountBo accountBo = (AccountBo) appContext.getBean("accountBo");
        List<Account> list = (List<Account>) accountBo.getAllAccount();
        MailBo mailBo = (MailBo) appContext.getBean("mailBo");

        Scheduler scheduler = new StdSchedulerFactory("quartz.properties").getScheduler();

        for (int i = 0; i < list.size(); i++) {
            Account a = list.get(i);
            String jobID = a.getAccount();
            MailingLogger.log.info("JobID is:" + jobID);
            a.setUidList(mailBo.getUIDList(jobID));
            JobDetail job = JobBuilder.newJob(PullingMailJob.class)
                    .withIdentity(jobID, "group1").build();
            job.getJobDataMap().put(PullingMailJob.ACCOUNT_FLAG, a);
            job.getJobDataMap().put(PullingMailJob.FTP_FLAG, ftp);
            job.getJobDataMap().put(PullingMailJob.MAIL_SAVER_FLAG, mailBo);

            long delay = new Date().getTime() + i * 5000l;

            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity(jobID, "group1")
                    .withSchedule(
                            SimpleScheduleBuilder.simpleSchedule()
                                    .withIntervalInSeconds(a.getInterval())
                                    .repeatForever()).startAt(new Date(delay))
                    .build();
            // Add job to scheduler
            scheduler.scheduleJob(job, trigger);
        }

        scheduler.start();

    }

}
