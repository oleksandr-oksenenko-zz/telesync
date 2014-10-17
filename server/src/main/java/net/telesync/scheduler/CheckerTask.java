package net.telesync.scheduler;

import net.telesync.model.DeviceInfo;
import net.telesync.service.DeviceService;
import net.telesync.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class CheckerTask implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(CheckerTask.class);

    private final DeviceService deviceService;
    private final MailService mailService;
    private final long outdatePeriod;
    private final Set<DeviceInfo> outdatedDevices = new HashSet<>();

    public CheckerTask(DeviceService deviceService, MailService mailService, long outdatePeriod) {
        this.deviceService = deviceService;
        this.mailService = mailService;
        this.outdatePeriod = outdatePeriod;
    }

    @Override
    public void run() {
        LOG.info("checking outdated devices");

        try {
            List<DeviceInfo> devices = deviceService.findOutdatedDevices(outdatePeriod);
            if (!devices.isEmpty()) {
                List<DeviceInfo> devicesCopy = new ArrayList<>(devices);

                devices.removeAll(outdatedDevices);

                if (!devices.isEmpty()) {
                    LOG.warn("There are outdated devices: {}", devices);
                    mailService.sendAlertMessage(devices);
                }

                outdatedDevices.addAll(devicesCopy);
                outdatedDevices.retainAll(devicesCopy);
            }
        } catch (Exception e) {
            LOG.error("Exception occurred while checking for outdated devices.", e);
        }
    }
}
