package net.telesync.web;

import net.telesync.model.DeviceInfo;
import net.telesync.model.RegisterDeviceRequest;
import net.telesync.service.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/heartbeat/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> makeHeartbeat(@PathVariable("id") Long deviceId) {
        deviceService.makeHeartbeat(deviceId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
