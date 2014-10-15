package net.telesync.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.LocalDateTime;

public class DeviceInfo {
    private final Integer id;
    private final String deviceUrl;
    private final String deviceName;
    private final String tvName;
    private final LocalDateTime lastHeartbeat;

    @JsonCreator
    public DeviceInfo(@JsonProperty("id") Integer id,
                      @JsonProperty("deviceUrl") String deviceUrl,
                      @JsonProperty("deviceName") String deviceName,
                      @JsonProperty("tvName") String tvName,
                      @JsonProperty("lastHeartbeat") LocalDateTime lastHeartbeat) {
        this.id = id;
        this.deviceUrl = deviceUrl;
        this.deviceName = deviceName;
        this.tvName = tvName;
        this.lastHeartbeat = lastHeartbeat;
    }

    public Integer getId() {
        return id;
    }

    public String getDeviceUrl() {
        return deviceUrl;
    }

    public LocalDateTime getLastHeartbeat() {
        return lastHeartbeat;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getTvName() {
        return tvName;
    }
}
