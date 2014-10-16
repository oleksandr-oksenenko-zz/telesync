package net.telesync.scheduler;

import net.telesync.model.DeviceInfo;
import net.telesync.service.DeviceService;
import net.telesync.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public final class CheckerTask implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(CheckerTask.class);

    private final DeviceService deviceService;
    private final MailService mailService;
    private final long outdatePeriod;

    public CheckerTask(DeviceService deviceService, MailService mailService, long outdatePeriod) {
        this.deviceService = deviceService;
        this.mailService = mailService;
        this.outdatePeriod = outdatePeriod;
    }

    @Override
    public void run() {
        LOG.info("checking outdated devices");

        List<DeviceInfo> devices = deviceService.findOutdatedDevices(outdatePeriod);
        if (!devices.isEmpty()) {
            LOG.warn("There are outdated devices");
            mailService.sendAlertMessage(devices);
        }
    }
}
