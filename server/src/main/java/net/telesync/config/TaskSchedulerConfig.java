package net.telesync.config;

import net.telesync.scheduler.CheckerTask;
import net.telesync.service.DeviceService;
import net.telesync.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Configuration
public class TaskSchedulerConfig {

    private static final Logger LOG = LoggerFactory.getLogger(TaskSchedulerConfig.class);

    private static final long CHECK_PERIOD = 30; // 5 minutes
    private static final long OUTDATE_PERIOD = 15 * 60; // 15 minutes

    @Bean
    @Autowired
    public ScheduledExecutorService scheduledExecutorService(DeviceService deviceService, MailService mailService) {
        LOG.info("initializing scheduled executor...");
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(checkerRunnable(deviceService, mailService), 0, CHECK_PERIOD, TimeUnit.SECONDS);
        return scheduler;
    }

    @Bean
    public Runnable checkerRunnable(DeviceService deviceService, MailService mailService) {
        return new CheckerTask(deviceService, mailService, OUTDATE_PERIOD);
    }

}
