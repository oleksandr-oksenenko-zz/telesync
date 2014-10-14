package net.telesync.web;

import net.telesync.model.DeviceInfo;
import net.telesync.model.RegisterDeviceRequest;
import net.telesync.service.DeviceService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ApiController {

    @Resource
    private DeviceService deviceService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public DeviceInfo registerDevice(@RequestBody RegisterDeviceRequest request) {
        return deviceService.registerNewDevice(request);
    }

    @RequestMapping(value = "/devices/{id}", method = RequestMethod.GET)
    public DeviceInfo getDeviceById(@PathVariable("id") Long deviceId) {
        deviceService.makeHeartbeat(deviceId);

        return deviceService.getById(deviceId);
    }

    @RequestMapping(value = "/devices", method = RequestMethod.GET)
    public List<DeviceInfo> getAllDevices() {
        return deviceService.getAll();
    }
}
