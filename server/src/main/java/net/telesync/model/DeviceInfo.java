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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeviceInfo that = (DeviceInfo) o;

        if (deviceName != null ? !deviceName.equals(that.deviceName) : that.deviceName != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (tvName != null ? !tvName.equals(that.tvName) : that.tvName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (deviceName != null ? deviceName.hashCode() : 0);
        result = 31 * result + (tvName != null ? tvName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "id=" + id +
                ", deviceUrl='" + deviceUrl + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", tvName='" + tvName + '\'' +
                ", lastHeartbeat=" + lastHeartbeat +
                '}';
    }
}
