package net.telesync.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.LocalDateTime;

public class DeviceInfo {
    private final Integer id;
    private final String url;
    private final String deviceName;
    private final String tvName;
    private final LocalDateTime lastHeartbeat;

    @JsonCreator
    public DeviceInfo(@JsonProperty("deviceId") Integer id,
                      @JsonProperty("deviceUrl") String url,
                      @JsonProperty("deviceName") String deviceName,
                      @JsonProperty("tvName") String tvName,
                      @JsonProperty("lastHeartbeat") LocalDateTime lastHeartbeat) {
        this.id = id;
        this.url = url;
        this.deviceName = deviceName;
        this.tvName = tvName;
        this.lastHeartbeat = lastHeartbeat;
    }

    public Integer getId() {
        return id;
    }

    public String getUrl() {
        return url;
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
