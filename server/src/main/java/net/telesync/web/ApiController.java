package net.telesync.web;

import net.telesync.model.DeviceInfo;
import net.telesync.model.RegisterDeviceRequest;
import net.telesync.service.DeviceService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/api")
public class ApiController {

    @Resource
    private DeviceService deviceService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public DeviceInfo registerDevice(@RequestBody RegisterDeviceRequest request) {
        return deviceService.registerNewDevice(request);
    }

}
