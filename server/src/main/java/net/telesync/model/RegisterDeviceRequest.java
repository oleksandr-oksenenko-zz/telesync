package net.telesync.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RegisterDeviceRequest {
    private final String deviceName;
    private final String tvName;

    @JsonCreator
    public RegisterDeviceRequest(@JsonProperty("deviceName") String deviceName,
                                 @JsonProperty("tvName") String tvName) {
        this.deviceName = deviceName;
        this.tvName = tvName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getTvName() {
        return tvName;
    }
}
