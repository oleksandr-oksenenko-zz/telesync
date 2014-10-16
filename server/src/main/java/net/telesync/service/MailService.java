package net.telesync.service;

import net.telesync.model.DeviceInfo;

import javax.ws.rs.core.Response;
import java.util.List;

public interface MailService {

    Response sendAlertMessage(List<DeviceInfo> device);

}
