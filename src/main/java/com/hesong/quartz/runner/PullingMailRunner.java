package com.hesong.quartz.runner;

import java.io.IOException;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;

import static org.quartz.TriggerBuilder.*;
import static org.quartz.CronScheduleBuilder.*;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import com.hesong.bo.AccountBo;
import com.hesong.bo.MailBo;
import com.hesong.mailEngine.tools.MailingLogger;
import com.hesong.model.Account;
import com.hesong.preload.Preload;
import com.hesong.quartz.job.PullingMailJob;

public class PullingMailRunner {
    @SuppressWarnings("unchecked")
    public void task(FTPClient ftp) throws IOException, SchedulerException {

        AccountBo accountBo = (AccountBo) Preload.APP_CONTEXT
                .getBean("accountBo");
        List<Account> list = (List<Account>) accountBo.getAllAccount();
        MailBo mailBo = (MailBo) Preload.APP_CONTEXT.getBean("mailBo");

        Scheduler scheduler = new StdSchedulerFactory("quartz.properties")
                .getScheduler();

        for (int i = 0; i < list.size(); i++) {
            Account a = list.get(i);
            String jobID = a.getAccount();
            MailingLogger.log.info("JobID is:" + jobID);
            a.setUidList(mailBo.getUIDList(jobID));
            JobDetail job = JobBuilder.newJob(PullingMailJob.class)
                    .withIdentity(jobID, PullingMailJob.JOB_GROUP).build();
            job.getJobDataMap().put(PullingMailJob.ACCOUNT_FLAG, a);
            job.getJobDataMap().put(PullingMailJob.FTP_FLAG, ftp);
            job.getJobDataMap().put(PullingMailJob.MAIL_SAVER_FLAG, mailBo);

            // long delay = new Date().getTime() + i * 5000l;

            int sec = (i * 7) % 60;
            int min = a.getInterval() / 60;
            String cronExpression = String.format("%d 0/%d 15-19 * * ?", sec,
                    min);
            MailingLogger.log.info("cronExpression: " + cronExpression);

            // Trigger trigger = TriggerBuilder
            // .newTrigger()
            // .withIdentity(jobID, PullingMailJob.JOB_GROUP)
            // .withSchedule(
            // SimpleScheduleBuilder.simpleSchedule()
            // .withIntervalInSeconds(a.getInterval())
            // .repeatForever()).startAt(new Date(delay))
            // .build();

            Trigger trigger = newTrigger()
                    .withIdentity(jobID, PullingMailJob.JOB_GROUP)
                    .withSchedule(cronSchedule(cronExpression)).forJob(job)
                    .build();

            // Add job to scheduler
            scheduler.scheduleJob(job, trigger);
        }

        scheduler.start();

    }

}
