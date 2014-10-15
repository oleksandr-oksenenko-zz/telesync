package net.telesync.service;

import net.telesync.model.DeviceInfo;
import net.telesync.model.RegisterDeviceRequest;

import java.util.List;

public interface DeviceService {

    DeviceInfo registerNewDevice(RegisterDeviceRequest request);

    List<DeviceInfo> getAll();

    DeviceInfo getById(Integer deviceId);

    void makeHeartbeat(Integer deviceId);

    void updateDeviceUrl(Integer deviceId, DeviceInfo deviceInfo);

    void removeDevice(Integer deviceId);
}
