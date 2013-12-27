package com.hesong.mailingPrototype;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.hesong.quartz.QuartzHello;

public class QuartzMain {

    public static void main(String[] args) throws SchedulerException {
        // TODO Auto-generated method stub
        JobDetail job = JobBuilder.newJob(QuartzHello.class).withIdentity("firstJob", "group1").build();
        
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("firstJob", "group1")
                .withSchedule(
                    SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(60).repeatForever())
                .build();
        
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);
    }

}
