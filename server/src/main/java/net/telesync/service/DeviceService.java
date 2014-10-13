package net.telesync.service;

import net.telesync.model.DeviceInfo;
import net.telesync.model.RegisterDeviceRequest;

import java.util.List;

public interface DeviceService {

    DeviceInfo registerNewDevice(RegisterDeviceRequest request);

    List<DeviceInfo> getAll();

}
